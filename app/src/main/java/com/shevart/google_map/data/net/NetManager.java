package com.shevart.google_map.data.net;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.shevart.google_map.data.AsyncDataCallback;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.util.LogUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("unused")
public class NetManager implements Net {
    private NetBridge netBridge;
    private ExecutorService executorService;

    public NetManager() {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        netBridge = new NetBridge();
    }

    @Override
    public void getPlaceByCoordinates(@NonNull String googlePlaceWebApiKey,
                                      @NonNull LatLng latLng,
                                      @NonNull AsyncDataCallback<TripPoint> callback) {
        executeNetRequest(
                NetRequestHelper.forPlaceByCoordinates(googlePlaceWebApiKey, latLng),
                NetCallbackResponseParseWrapper.wrapCallbackForPlaceByCoordinates(callback));
    }

    private void executeNetRequest(@NonNull NetRequest netRequest, @NonNull AsyncDataCallback<String> callback) {
        executorService.execute(new NetRequestExecutorThread(netRequest, callback, netBridge));
    }
}