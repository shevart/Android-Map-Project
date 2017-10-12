package com.shevart.google_map.util;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.shevart.google_map.models.TripPoint;

@SuppressWarnings("WeakerAccess")
public class BundleUtils {
    private static final String TRIP_POINT_KEY = "tripPoint";

    public static void setTripPoint(@NonNull Intent intent, @NonNull TripPoint tripPoint) {
        intent.putExtra(TRIP_POINT_KEY, tripPoint);
    }

    public static TripPoint getTripPoint(@NonNull Intent intent) {
        return intent.getParcelableExtra(TRIP_POINT_KEY);
    }
}