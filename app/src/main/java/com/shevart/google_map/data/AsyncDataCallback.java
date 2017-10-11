package com.shevart.google_map.data;

import android.support.annotation.NonNull;

public interface AsyncDataCallback<T> {
    void onResult(@NonNull T data);

    void onError(@NonNull Exception e);
}
