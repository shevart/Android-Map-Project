package com.shevart.google_map.data.net;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.shevart.google_map.app.TokenProvider;
import com.shevart.google_map.data.AsyncDataCallback;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.util.SystemUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.shevart.google_map.util.Util.checkNonNull;

@SuppressWarnings("unused")
public class NetManager implements Net {
    private final NetBridge netBridge;
    private final ExecutorService executorService;
    private final TokenProvider tokenProvider;
    // change it in the future!
    private Context context;

    public NetManager(@NonNull Context context, @NonNull TokenProvider tokenProvider) {
        checkNonNull(context);
        checkNonNull(tokenProvider);
        this.context = context;
        this.tokenProvider = tokenProvider;
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        netBridge = new NetBridge();
    }

    @Override
    public boolean isNetConnectionAvailable() {
        return SystemUtils.isNetworkAvailable(context);
    }

    @Override
    public void getPlaceByCoordinates(@NonNull LatLng latLng,
                                      @NonNull AsyncDataCallback<TripPoint> callback) {
        executeNetRequest(
                NetRequestHelper.forPlaceByCoordinates(tokenProvider.getGooglePlaceWebApiKey(), latLng),
                NetCallbackResponseParseWrapper.wrapCallbackForPlaceByCoordinates(callback));
    }

    private void executeNetRequest(@NonNull NetRequest netRequest, @NonNull AsyncDataCallback<String> callback) {
        executorService.execute(new NetRequestExecutorThread(netRequest, callback, netBridge));
    }
}