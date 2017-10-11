package com.shevart.google_map.util;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;

@SuppressWarnings("unused")
public class SystemUtils {
    public static boolean isNetworkAvailable(@NonNull Context context) {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static class GPS {
        public static boolean isGPSEnabled(@NonNull Context context) {
            final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        }

        public static void turnGPSOn(@NonNull Context context) {
            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }
}