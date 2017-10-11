package com.shevart.google_map.ui.google_map;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shevart.google_map.R;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.util.MapUtils;

import static com.shevart.google_map.util.Util.checkNonNull;

public class GoogleMapViewHelper implements OnMapReadyCallback {
    private static final String USER_LOCATION_TAG = "userLocationTag";
    private Context context;
    private GoogleMap googleMap;

    private Marker currentLocationMarker;
    private LatLng lastLatLng;

    public GoogleMapViewHelper(@NonNull Context context) {
        checkNonNull(context);
        this.context = context.getApplicationContext();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        MapUtils.showUkraineOnTheMap(googleMap);
    }

    public void updateUserLocation(@NonNull LatLng latLng) {
        if (googleMap == null)
            return;
        lastLatLng = latLng;
        removeMyLocationMarker();
        currentLocationMarker = googleMap
                .addMarker(MapUtils.createMarkerOptionsCurrentLocation(latLng, "Current location"));
        currentLocationMarker.setTag(USER_LOCATION_TAG);
    }

    private void removeMyLocationMarker() {
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
    }

    public void showUserLocation() {
        if (currentLocationMarker != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocationMarker.getPosition()));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }
    }

    public void hideUserLocation() {
        removeMyLocationMarker();
    }

    public boolean isUserLocation(@NonNull Marker marker) {
        return marker.getTag() == USER_LOCATION_TAG;
    }

    public void showTripRouteView(@Nullable TripPoint startTripPoint, @Nullable TripPoint finishTripPoint) {
        if (startTripPoint != null && finishTripPoint != null) {
            showTwoPointsWithTripRouteOnTheMap(startTripPoint, finishTripPoint);
            return;
        }

        googleMap.clear();
        if (startTripPoint != null) {
            showTripPointOnTheMap(startTripPoint);
        } else if (finishTripPoint != null) {
            showTripPointOnTheMap(finishTripPoint);
        }

        if (lastLatLng != null) {
            updateUserLocation(lastLatLng);
        }
    }

    private void showTripPointOnTheMap(@NonNull TripPoint tripPoint) {
        addPointToMapAndZoom(MapUtils.createMarkerOptions(
                tripPoint.getLatLng(),
                tripPoint.getName(),
                R.drawable.ic_location_on_black_24dp));
    }

    private void addPointToMapAndZoom(@NonNull final MarkerOptions markerOptions) {
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerOptions.getPosition()));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    private void showTwoPointsWithTripRouteOnTheMap(@NonNull TripPoint startTripPoint,
                                                    @NonNull TripPoint finishTripPoint) {

        MapUtils.showFromToPlacesOnTheMap(context, googleMap, startTripPoint, finishTripPoint);
        drawRouteBetweenTwoTripPoints(startTripPoint, finishTripPoint);
    }

    private void drawRouteBetweenTwoTripPoints(@NonNull TripPoint startTripPoint, @NonNull TripPoint finishTripPoint) {
        // TODO: 12.10.17 handle it!
    }
}