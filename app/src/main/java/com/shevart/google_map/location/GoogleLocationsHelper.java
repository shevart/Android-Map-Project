package com.shevart.google_map.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.shevart.google_map.util.LogUtil;
import com.shevart.google_map.util.PermissionsUtils;

import static com.shevart.google_map.util.Util.checkNonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public class GoogleLocationsHelper implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private Context context;
    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates;

    private OnGoogleLocationsUpdateListener locationsUpdateListener;

    public GoogleLocationsHelper(@NonNull Context context, @NonNull OnGoogleLocationsUpdateListener locationsUpdateListener) {
        checkNonNull(context);
        checkNonNull(locationsUpdateListener);
        this.context = context;
        this.locationsUpdateListener = locationsUpdateListener;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    public void onStart() {
        mGoogleApiClient.connect();
    }

    public void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void onResume() {
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    public void onPause() {
        stopLocationUpdates();
    }

    public void onUserLocationPermissionAccepted() {
        onResume();
    }

    public Location getCurrentLocation() {
        return mCurrentLocation;
    }

    private void startLocationUpdates() {
        if (!PermissionsUtils.UserLocationPermission.isNeedRequest(context)) {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            } catch (SecurityException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LogUtil.e("GoogleLocationsHelper - onConnected()");
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        LogUtil.e("GoogleLocationsHelper - onConnectionSuspended() " + i);
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        LogUtil.e("GoogleLocationsHelper - onConnectionFailed() " + connectionResult.getErrorMessage());
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        if (locationsUpdateListener != null) {
            locationsUpdateListener.onLocationChanged(mCurrentLocation);
        }
    }

    public interface OnGoogleLocationsUpdateListener {
        void onLocationChanged(@NonNull Location location);
    }
}