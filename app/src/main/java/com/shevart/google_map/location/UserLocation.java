package com.shevart.google_map.location;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface UserLocation {
    @Nullable
    LatLng getLastLocation();

    void addLocationEventsListener(@NonNull UserLocationManager.LocationEventsListener eventsListener);

    void removeLocationEventsListener(@NonNull UserLocationManager.LocationEventsListener eventsListener);

    boolean isGPSEnabled();

    void onUserLocationPermissionAccepted();

    void requestLastUserLocation(@NonNull UserLocationManager.LocationEventsListener eventsListener);
}