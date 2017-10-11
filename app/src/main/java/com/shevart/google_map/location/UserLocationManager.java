package com.shevart.google_map.location;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.shevart.google_map.util.LogUtil;
import com.shevart.google_map.util.SystemUtils;

import java.util.ArrayList;
import java.util.List;

import static com.shevart.google_map.util.Util.checkNonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public class UserLocationManager implements
        UserLocation,
        GPSUpdateStatusHelper.GPSStatusChangedListener,
        GoogleLocationsHelper.OnGoogleLocationsUpdateListener {

    private Context context;
    private GPSUpdateStatusHelper gpsUpdateStatusHelper;
    private GoogleLocationsHelper googleLocationsHelper;
    private List<LocationEventsListener> locationEventsListeners = new ArrayList<>();

    private boolean isWorked = false;
    private LatLng currentLocation;

    public UserLocationManager(@NonNull Context context) {
        checkNonNull(context);
        this.context = context;
        gpsUpdateStatusHelper = new GPSUpdateStatusHelper(this);
        googleLocationsHelper = new GoogleLocationsHelper(context, this);
    }

    @Override
    public void onGPSEnabled() {
        determineFurtherWork();
        for (LocationEventsListener eventsListener : locationEventsListeners) {
            eventsListener.onGPSSignalAppeared();
        }
    }

    @Override
    public void onGPSDisabled() {
        determineFurtherWork();
        for (LocationEventsListener eventsListener : locationEventsListeners) {
            eventsListener.onGPSSignalDisappeared();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        determineFurtherWork();
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        for (LocationEventsListener eventsListener : locationEventsListeners) {
            eventsListener.onUserLocationChanged(currentLocation);
        }

    }

    private void determineFurtherWork() {
        if (isWorked && locationEventsListeners.isEmpty()) {
            stop();
            return;
        }

        if (!isWorked && !locationEventsListeners.isEmpty()) {
            start();
        }
    }

    private void start() {
        LogUtil.e("userLocation - START()");
        isWorked = true;
        gpsUpdateStatusHelper.start(context);
        googleLocationsHelper.onStart();
        googleLocationsHelper.onResume();
    }

    private void stop() {
        LogUtil.e("userLocation - STOP()");
        isWorked = false;
        gpsUpdateStatusHelper.stop(context);
        googleLocationsHelper.onPause();
        googleLocationsHelper.onStop();
    }

    @Nullable
    @Override
    public LatLng getLastLocation() {
        return currentLocation;
    }

    @Override
    public void addLocationEventsListener(@NonNull LocationEventsListener eventsListener) {
        checkNonNull(eventsListener);
        locationEventsListeners.add(eventsListener);
        determineFurtherWork();
    }

    @Override
    public void removeLocationEventsListener(@NonNull LocationEventsListener eventsListener) {
        checkNonNull(eventsListener);
        locationEventsListeners.remove(eventsListener);
        determineFurtherWork();
    }

    @Override
    public boolean isGPSEnabled() {
        return SystemUtils.GPS.isGPSEnabled(context);
    }

    @Override
    public void onUserLocationPermissionAccepted() {
        googleLocationsHelper.onUserLocationPermissionAccepted();
    }

    @Override
    public void requestLastUserLocation(@NonNull UserLocationManager.LocationEventsListener eventsListener) {
        if (currentLocation != null) {
            eventsListener.onUserLocationChanged(currentLocation);
        }
    }

    public interface LocationEventsListener {
        void onUserLocationChanged(@NonNull LatLng myNewLocation);

        void onGPSSignalAppeared();

        void onGPSSignalDisappeared();
    }
}