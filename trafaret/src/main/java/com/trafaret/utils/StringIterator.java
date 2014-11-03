package com.trafaret.utils;

import java.util.Iterator;
import java.util.Objects;

public class StringIterator implements Iterator<Character> {

    private final String string;
    int i = 0;

    public StringIterator(String string) {
        Objects.requireNonNull(string);
        this.string = string;
    }

    @Override
    public boolean hasNext() {
        return i <= string.length();
    }

    @Override
    public Character next() {
        return string.charAt(i++);
    }
}
