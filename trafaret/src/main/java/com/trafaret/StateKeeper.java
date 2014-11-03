package com.trafaret;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StateKeeper {
    private State state;
    Set<State> starts = new HashSet<>();
    Map<State, State> endStartMap = new HashMap<>();
    Map<State, StringBuilder> openExpressions = new HashMap<>();
    Map<Pair<State, State>, String> lastExpressions = new HashMap<>();

    private StateKeeper(State state) {
        this.state = state;
    }
    public static StateKeeper init() {
        return new StateKeeper(State.PLAIN_TEXT);
    }

    public StateKeeper track(State start, State end) {
        starts.add(start);
        endStartMap.put(end, start);
        return this;
    }
    public void next(char c) {
        state = state.next(c);
        if (starts.contains(state)) {
            StringBuilder seqBuilder = openExpressions.get(state);
            if (seqBuilder == null) {
                seqBuilder = new StringBuilder();
                openExpressions.put(state, seqBuilder);
            }
        }
        for (State start : openExpressions.keySet()) {
            StringBuilder seqBuilder = openExpressions.get(start);
            seqBuilder.append(c);
        }
        if (endStartMap.containsKey(state)) {
            State startState = endStartMap.get(state);
            Pair pair = Pair.of(startState, state);
            StringBuilder seqBuilder = openExpressions.get(startState);
            String seq = seqBuilder.toString();
            lastExpressions.put(pair, seq);
            openExpressions.remove(startState);
        }
    }
    public boolean is(State other) {
        return state == other;
    }

    public String getExpression(Pair<State, State> pair) {
        return Strings.nullToEmpty(lastExpressions.get(pair));
    }

    public static enum State {

        PLAIN_TEXT {
            @Override
            State next(char c) {
                if (c == '$') return DOLLAR;
                return PLAIN_TEXT;
            }
        },
        DOLLAR {
            @Override
            State next(char c) {
                if (c == '{') return DOLLAR_BRACKET;
                return PLAIN_TEXT;
            }
        },
        DOLLAR_BRACKET {
            @Override
            State next(char c) {
                if (c == '}') return END_EXPRESSION;
                return EXPRESSION;
            }
        },
        EXPRESSION {
            @Override
            State next(char c) {
                if (c == '}') return END_EXPRESSION;
                return EXPRESSION;
            }
        },
        END_EXPRESSION {
            @Override
            State next(char c) {
                if (c == '$') return DOLLAR;
                return PLAIN_TEXT;
            }
        };

        abstract State next(char c);
    }
}

