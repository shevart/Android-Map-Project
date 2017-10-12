package com.shevart.google_map.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.shevart.google_map.ui.screens.select_last_place.SelectPlaceFromHistoryActivity;

@SuppressWarnings("WeakerAccess")
public class Launcher {
    public static final int SELECT_START_TRIP_POINT_FROM_HISTORY_CODE = 1;
    public static final int SELECT_END_TRIP_POINT_FROM_HISTORY_CODE = 2;

    public static class ActivityComponents {
        public static void startTripPointSelectFromHistory(@NonNull Activity activity) {
            Intent intent = new Intent(activity, SelectPlaceFromHistoryActivity.class);
            activity.startActivityForResult(intent, SELECT_START_TRIP_POINT_FROM_HISTORY_CODE);
        }

        public static void endTripPointSelectFromHistory(@NonNull Activity activity) {
            Intent intent = new Intent(activity, SelectPlaceFromHistoryActivity.class);
            activity.startActivityForResult(intent, SELECT_END_TRIP_POINT_FROM_HISTORY_CODE);
        }
    }
}