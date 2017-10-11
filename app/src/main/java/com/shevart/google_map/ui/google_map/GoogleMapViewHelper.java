package com.shevart.google_map.ui.google_map;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.shevart.google_map.util.MapUtils;

import static com.shevart.google_map.util.Util.checkNonNull;

public class GoogleMapViewHelper implements OnMapReadyCallback {
    private Context context;
    private GoogleMap googleMap;

    private Marker currentLocationMarker;

    public GoogleMapViewHelper(@NonNull Context context) {
        checkNonNull(context);
        context = context.getApplicationContext();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        MapUtils.showUkraineOnTheMap(googleMap);
    }

    public void updateUserLocation(@NonNull LatLng latLng) {
        if (googleMap == null)
            return;
        removeMyLocationMarker();
        currentLocationMarker = googleMap.addMarker(
                MapUtils.createMarkerOptionsCurrentLocation(latLng, "Current location"));
    }

    private void removeMyLocationMarker() {
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
    }
}