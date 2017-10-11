package com.shevart.google_map.ui.screens.main;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.ui.mvp.BasePresenter;
import com.shevart.google_map.ui.mvp.BaseView;

interface MainScreenContract {
    interface Presenter extends BasePresenter<View> {
        void onStartTripPointCoordinatesSelected(@NonNull LatLng latLng);

        void onEndTripPointCoordinatesSelected(@NonNull LatLng latLng);
    }

    interface View extends BaseView {
        void onStartTripRouteSelected(@NonNull TripPoint tripPoint);

        void onEndTripRouteSelected(@NonNull TripPoint tripPoint);

        void showErrorPlaceMessage();
    }
}