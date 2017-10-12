package com.shevart.google_map.ui.screens.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.ui.mvp.BasePresenter;
import com.shevart.google_map.ui.mvp.BaseView;

interface MainScreenContract {
    interface Presenter extends BasePresenter<View> {
        void onStartTripPointCoordinatesSelected(@NonNull LatLng latLng);

        void onEndTripPointCoordinatesSelected(@NonNull LatLng latLng);

        void onStartTripPointSelectedFromHistory(@NonNull TripPoint tripPoint);

        void onEndTripPointSelectedFromHistory(@NonNull TripPoint tripPoint);

        void drawRoute();

        @Nullable
        TripPoint getStartTripPoint();

        @Nullable
        TripPoint getEndTripPoint();
    }

    interface View extends BaseView {
        void onStartTripRouteSelected(@NonNull TripPoint tripPoint);

        void onEndTripRouteSelected(@NonNull TripPoint tripPoint);

        void showErrorPlaceMessage();

        void drawRoute(@NonNull PolylineOptions polylineOptions);

        void showErrorDrawRoute();
    }
}