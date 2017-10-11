package com.shevart.google_map.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import com.shevart.google_map.util.LogUtil;
import com.shevart.google_map.util.SystemUtils;

import static com.shevart.google_map.util.Util.checkNonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public class GPSUpdateStatusHelper {
    private static final String GPS_KEY = "android.location.PROVIDERS_CHANGED";
    private BroadcastReceiver gpsReceiver;

    private GPSStatusChangedListener gpsStatusChangedListener;

    public GPSUpdateStatusHelper(@NonNull GPSStatusChangedListener gpsStatusChangedListener) {
        checkNonNull(gpsStatusChangedListener);
        this.gpsStatusChangedListener = gpsStatusChangedListener;
        gpsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().matches(GPS_KEY)) {
                    onGPSStatusChanged(context);
                }
            }
        };
    }

    private void onGPSStatusChanged(@NonNull Context context) {
        if (gpsStatusChangedListener == null) return;
        if (SystemUtils.GPS.isGPSEnabled(context)) {
            gpsStatusChangedListener.onGPSEnabled();
        } else {
            gpsStatusChangedListener.onGPSDisabled();
        }
    }

    public void start(@NonNull Context context) {
        context.registerReceiver(gpsReceiver, new IntentFilter(GPS_KEY));
    }

    public void stop(@NonNull Context context) {
        // try-catch for if receiver no register
        try {
            context.unregisterReceiver(gpsReceiver);
        } catch (IllegalArgumentException e) {
            LogUtil.e(e);
        }
    }

    public interface GPSStatusChangedListener {
        void onGPSEnabled();

        void onGPSDisabled();
    }
}