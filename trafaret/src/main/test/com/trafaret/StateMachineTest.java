package com.trafaret;

import com.trafaret.state.StateMachine;
import com.trafaret.utils.Chars;
import com.trafaret.utils.Strings;
import junit.framework.Assert;
import org.junit.Test;

public class StateMachineTest {

    @Test
    public void validateNumber() {
        StateMachine sm = StateMachine.init()
                .handle("start", Chars.of('-', '+'), "sign")
                .handle("sign", Chars.DIGITS, "number")
                .handle("number", Chars.DIGITS, "number")
                .handle("number", '.', "dot")
                .handle("dot", Chars.DIGITS, "decimal")
                .handle("decimal", Chars.DIGITS, "decimal")
                .handle("decimal", ' ', "end");

        sm.begin();
        Strings.iterate("-123.56E2 ").forEach((c) -> sm.take(c));
        Assert.assertEquals(sm.getMessage(), "error", sm.getState());

        sm.begin();
        Strings.iterate("-123.6 ").forEach((c) -> sm.take(c));
        Assert.assertEquals(sm.getMessage(), "end", sm.getState());
    }

    @Test
    public void templateEngine() {
        St
    }
}
