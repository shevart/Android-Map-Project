package com.shevart.google_map.util;

public class Util {
    public static void checkNonNull(Object o) {
        if (o == null)
            throw new IllegalArgumentException("Must be non null!");
    }
}