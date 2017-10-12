package com.shevart.google_map.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

@SuppressWarnings({"WeakerAccess", "unused", "FieldCanBeLocal"})
public class UiNotificationsUtils {
    private static boolean debug = true;

    public static void showShortToast(@NonNull Context context, @NonNull String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showDevMessage(@NonNull Context context, @NonNull String msg) {
        if (debug) {
            showShortToast(context, msg);
            LogUtil.e(msg);
        }
    }

    public static class Extra {
        public static void developerSeeToLogsMsg(@NonNull Context context) {
            showDevMessage(context, "Developer, go to logs!");
        }
    }
}
