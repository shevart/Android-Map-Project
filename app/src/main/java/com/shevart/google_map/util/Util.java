package com.shevart.google_map.util;

import android.support.annotation.Nullable;

public class Util {
    public static void checkNonNull(Object o) {
        if (o == null)
            throw new IllegalArgumentException("Must be non null!");
    }

    public static boolean isNullOrEmpty(@Nullable String s) {
        return s == null || s.isEmpty();
    }
}