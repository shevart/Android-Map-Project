package com.shevart.google_map.ui.mvp;

import android.support.annotation.NonNull;

public interface BasePresenter<V extends BaseView> {
    void attachView(@NonNull V view);

    void detachView();
}
