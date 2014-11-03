package com.trafaret.utils;

import java.util.HashSet;
import java.util.Set;

public class Chars {
    public static final Set<Character> DIGITS = Chars.range('0', '9');
    public static final Set<Character> LOWERCASE_ABC = Chars.range('a', 'z');
    public static final Set<Character> UPPERCASE_ABC = Chars.range('A', 'Z');
    public static final Set<Character> ABC;
    static {
        ABC = new HashSet<>();
        ABC.addAll(LOWERCASE_ABC);
        ABC.addAll(UPPERCASE_ABC);
    }


    private static Set<Character> range(char start, char end) {
        Set<Character> result = new HashSet<>();
        char c = start;
        while (c <= end) {
            result.add(c++);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Chars.DIGITS);
        System.out.println(Chars.LOWERCASE_ABC);
        System.out.println(Chars.UPPERCASE_ABC);
        System.out.println(Chars.ABC);
    }
}
