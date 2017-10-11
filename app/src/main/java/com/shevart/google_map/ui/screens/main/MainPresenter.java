package com.shevart.google_map.ui.screens.main;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.shevart.google_map.data.AsyncDataCallback;
import com.shevart.google_map.data.net.Net;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.ui.mvp.AbsPresenter;
import com.shevart.google_map.util.ErrorUtil;

import static com.shevart.google_map.util.Util.checkNonNull;
import static com.shevart.google_map.util.Util.isNullOrEmpty;

public class MainPresenter extends AbsPresenter<MainScreenContract.View>
        implements MainScreenContract.Presenter {
    private Net net;

    public MainPresenter(@NonNull Net net) {
        checkNonNull(net);
        this.net = net;
    }

    private TripPoint startTripPoint;
    private TripPoint endTripPoint;

    @Override
    public void onStartTripPointCoordinatesSelected(@NonNull final LatLng latLng) {
        if (!net.isNetConnectionAvailable()) {
            getView().showNoInternetConnectionError();
            return;
        }

        getView().showProgress();
        net.getPlaceByCoordinates(latLng, new AsyncDataCallback<TripPoint>() {
            @Override
            public void onResult(@NonNull TripPoint data) {
                if (isValidPlaceWithErrorAlert(data)) {
                    startTripPoint = data;
                    startTripPoint.setLatLng(latLng);
                    if (isViewAttach()) {
                        getView().hideProgress();
                        getView().onStartTripRouteSelected(startTripPoint);
                    }
                }
            }

            @Override
            public void onError(@NonNull Exception e) {
                if (isViewAttach()) {
                    getView().hideProgress();
                    getView().showError(ErrorUtil.convert(e));
                }
            }
        });
    }

    @Override
    public void onEndTripPointCoordinatesSelected(@NonNull final LatLng latLng) {
        if (!net.isNetConnectionAvailable()) {
            getView().showNoInternetConnectionError();
            return;
        }

        getView().showProgress();
        net.getPlaceByCoordinates(latLng, new AsyncDataCallback<TripPoint>() {
            @Override
            public void onResult(@NonNull TripPoint data) {
                if (isValidPlaceWithErrorAlert(data)) {
                    endTripPoint = data;
                    endTripPoint.setLatLng(latLng);
                    if (isViewAttach()) {
                        getView().hideProgress();
                        getView().onEndTripRouteSelected(endTripPoint);
                    }
                }
            }

            @Override
            public void onError(@NonNull Exception e) {
                if (isViewAttach()) {
                    getView().hideProgress();
                    getView().showError(ErrorUtil.convert(e));
                }
            }
        });
    }

    private boolean isValidPlaceWithErrorAlert(@NonNull TripPoint tripPoint) {
        if (isNullOrEmpty(tripPoint.getAddress())) {
            if (isViewAttach()) {
                getView().hideProgress();
                getView().showErrorPlaceMessage();
            }
            return false;
        }
        return true;
    }
}
