package com.shevart.google_map.data.net;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.shevart.google_map.data.AsyncDataCallback;

@SuppressWarnings("unused")
class NetBridge extends Handler {
    <T> void postResponse(@NonNull final T response, @NonNull final AsyncDataCallback<T> callback) {
        post(new Runnable() {
            @Override
            public void run() {
                callback.onResult(response);
            }
        });
    }

    void postError(@NonNull final Exception e, @NonNull final AsyncDataCallback callback) {
        post(new Runnable() {
            @Override
            public void run() {
                callback.onError(e);
            }
        });
    }
}