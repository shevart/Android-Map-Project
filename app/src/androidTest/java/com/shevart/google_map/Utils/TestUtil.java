package com.shevart.google_map.Utils;

import android.support.annotation.NonNull;

import com.shevart.google_map.models.TripPoint;

public class TestUtil {
    public static boolean isEquals(@NonNull TripPoint first, @NonNull TripPoint second) {
        boolean equals = true;
        if (!first.getName().equals(second.getName()))
            equals = false;
        if (!first.getAddress().equals(second.getAddress()))
            equals = false;
        if (!first.getLatLng().equals(second.getLatLng()))
            equals = false;
        return equals;
    }
}
