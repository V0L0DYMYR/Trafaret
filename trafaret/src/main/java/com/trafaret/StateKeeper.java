package com.trafaret;

import java.util.HashMap;
import java.util.Map;

public class StateKeeper {
    private State state;

    Map<State, StringBuilder> openExpressions = new HashMap<>();
    Map<Pair<State, State>, String> lastExpressions = new HashMap<>();

    private StateKeeper(State state) {
        this.state = state;
    }
    public static StateKeeper init() {
        return new StateKeeper(State.PLAIN_TEXT);
    }
    public void next(char c) {
        state = state.next(c);
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

