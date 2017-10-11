package com.shevart.google_map.data.net;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.shevart.google_map.data.AsyncDataCallback;
import com.shevart.google_map.models.TripPoint;

@SuppressWarnings("WeakerAccess")
public interface Net {
    void getPlaceByCoordinates(@NonNull String googlePlaceWebApiKey,
                               @NonNull LatLng latLng,
                               @NonNull AsyncDataCallback<TripPoint> callback);
}
