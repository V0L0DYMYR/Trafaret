package com.trafaret.state;

import com.trafaret.utils.Pair;
import com.trafaret.utils.Strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class StateMachine {
    private State state;
    Set<State> starts = new HashSet<>();
    Map<State, State> endStartMap = new HashMap<>();
    Map<State, StringBuilder> openExpressions = new HashMap<>();
    Map<Pair<State, State>, String> lastExpressions = new HashMap<>();

    private StateMachine(State state) {
        this.state = state;
    }
    public static StateMachine init() {
        return new StateMachine(State.PLAIN_TEXT);
    }

    public StateMachine track(State start, State end) {
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

    public StateMachine define(String sign, char... chars) {
        return this;
    }

    public StateMachine handle(String from, char c, String to, Function<String, String> handler) {
        return this;
    }

    public StateMachine handle(String from, Set<Character> c, String to, Function<String, String> handler) {
        return this;
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

