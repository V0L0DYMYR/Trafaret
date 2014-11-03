package com.trafaret;

import com.trafaret.state.StateMachine;
import com.trafaret.utils.Chars;
import com.trafaret.utils.Strings;
import org.junit.Test;

public class StateKeeperTest {

    @Test
    public void test() {
        StateMachine sk = StateMachine.init()
                .define("sign", '-', '+')
                .handle("sign", Chars.DIGITS, "number", (i) -> i)
                .handle("number", '.', "dot", (i) -> i);

        Strings.iterate("-123.56").forEach((c) -> sk.next(c));
    }
}
