package com.shevart.google_map.ui.screens.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.shevart.google_map.data.AsyncDataCallback;
import com.shevart.google_map.data.db.DB;
import com.shevart.google_map.data.net.Net;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.ui.mvp.AbsPresenter;
import com.shevart.google_map.util.ErrorUtil;

import static com.shevart.google_map.util.Util.checkNonNull;
import static com.shevart.google_map.util.Util.isNullOrEmpty;

class MainPresenter extends AbsPresenter<MainScreenContract.View>
        implements MainScreenContract.Presenter {
    private TripPoint startTripPoint;
    private TripPoint endTripPoint;

    private Net net;
    private DB db;

    MainPresenter(@NonNull Net net, @NonNull DB db) {
        checkNonNull(net);
        checkNonNull(db);
        this.net = net;
        this.db = db;
    }

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
                    db.save(startTripPoint);
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
                    db.save(endTripPoint);
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

    @Override
    public void onStartTripPointSelectedFromHistory(@NonNull TripPoint tripPoint) {
        startTripPoint = tripPoint;
        getView().onStartTripRouteSelected(startTripPoint);
    }

    @Override
    public void onEndTripPointSelectedFromHistory(@NonNull TripPoint tripPoint) {
        endTripPoint = tripPoint;
        getView().onEndTripRouteSelected(endTripPoint);
    }

    @Override
    public void drawRoute() {
        if (startTripPoint == null || endTripPoint == null) {
            getView().showErrorDrawRoute();
            return;
        }

        if (!net.isNetConnectionAvailable()) {
            getView().showNoInternetConnectionError();
            return;
        }

        getView().showProgress();
        net.getRouteByCoordinates(startTripPoint.getLatLng(), endTripPoint.getLatLng(),
                new AsyncDataCallback<PolylineOptions>() {
                    @Override
                    public void onResult(@NonNull PolylineOptions data) {
                        if (isViewAttach()) {
                            getView().hideProgress();
                            getView().drawRoute(data);
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

    @Nullable
    @Override
    public TripPoint getStartTripPoint() {
        return startTripPoint;
    }

    @Nullable
    @Override
    public TripPoint getEndTripPoint() {
        return endTripPoint;
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
