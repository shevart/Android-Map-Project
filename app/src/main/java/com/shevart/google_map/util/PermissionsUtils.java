package com.shevart.google_map.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PermissionsUtils {
    public static class UserLocationPermission {
        private final static String[] ACCESS_FINE_LOCATION = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        public static boolean isNeedRequest(@NonNull Context context) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                return false;
            final int permissionCheck = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            return !(permissionCheck == PackageManager.PERMISSION_GRANTED);
        }

        public static void request(@NonNull Activity activity, int requestCode) {
            ActivityCompat.requestPermissions(activity, ACCESS_FINE_LOCATION, requestCode);
        }

        public static void request(@NonNull Fragment fragment, int requestCode) {
            fragment.requestPermissions(ACCESS_FINE_LOCATION, requestCode);
        }

        public static boolean isAllowed(@NonNull String[] permissions, @NonNull int[] grantResults) {
            if (!permissions[0].equals(ACCESS_FINE_LOCATION[0])) {
                throw new RuntimeException("Different permissions names!");
            }
            return grantResults[0] == 0;
        }
    }
}
