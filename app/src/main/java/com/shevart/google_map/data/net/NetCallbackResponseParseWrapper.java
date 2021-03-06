package com.shevart.google_map.data.net;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.shevart.google_map.data.AsyncDataCallback;
import com.shevart.google_map.data.net.response.GetGoogleMapRouteResponse;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.util.MapUtils;
import com.shevart.google_map.util.NetResponseParseUtil;

import org.json.JSONException;

/***
 * There is converter for net responses - String to expected object from Net clients.
 *
 * For example in UI layer we expect list with some data. Here we parse response from server which is String
 * and pass to callback from client our result or error.
 */
class NetCallbackResponseParseWrapper {
    static AsyncDataCallback<String> wrapCallbackForPlaceByCoordinates(@NonNull final AsyncDataCallback<TripPoint> callback) {
        return new AsyncDataCallback<String>() {
            @Override
            public void onResult(@NonNull String data) {
                try {
                    callback.onResult(NetResponseParseUtil.stringToTripPoint(data));
                } catch (JSONException e) {
                    callback.onError(e);
                }
            }

            @Override
            public void onError(@NonNull Exception e) {
                callback.onError(e);
            }
        };
    }

    static AsyncDataCallback<String> wrapCallbackForRouteByCoordinates(@NonNull final AsyncDataCallback<PolylineOptions> callback) {
        return new AsyncDataCallback<String>() {
            @Override
            public void onResult(@NonNull String data) {
                try {
                    final GetGoogleMapRouteResponse response = new Gson().fromJson(data, GetGoogleMapRouteResponse.class);
                    if (response.getStatus().equalsIgnoreCase("ok")) {
                        callback.onResult(MapUtils.createPolyline(response.getRoutes().get(0).getLegs().get(0).getSteps()));
                    } else {
                        // todo change it!
                        callback.onError(new NullPointerException());
                    }
                } catch (Exception e) {
                    callback.onError(e);
                }
            }

            @Override
            public void onError(@NonNull Exception e) {
                callback.onError(e);
            }
        };
    }
}