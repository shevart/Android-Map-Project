package com.shevart.google_map.util;

import android.support.annotation.NonNull;

public class ErrorUtil {
    public static String convert(@NonNull Exception e) {
        return e.getMessage();
    }
}
