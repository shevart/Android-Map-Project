package com.shevart.google_map.util;

import android.support.annotation.Nullable;

import java.util.List;

public class Util {
    public static void checkNonNull(Object o) {
        if (o == null)
            throw new IllegalArgumentException("Must be non null!");
    }

    public static boolean isNullOrEmpty(@Nullable String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNullOrEmpty(@Nullable List l) {
        return l == null || l.isEmpty();
    }
}