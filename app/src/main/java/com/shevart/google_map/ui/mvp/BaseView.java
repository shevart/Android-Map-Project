package com.shevart.google_map.ui.mvp;

import android.support.annotation.NonNull;

public interface BaseView {
    void showError(@NonNull String errorMessage);

    void showProgress();

    void hideProgress();

    void showNoInternetConnectionError();
}