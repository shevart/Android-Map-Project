package com.shevart.google_map.ui.screens.select_last_place;

import android.support.annotation.NonNull;

import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.ui.mvp.BasePresenter;
import com.shevart.google_map.ui.mvp.BaseView;

import java.util.List;

interface SelectLastPlaceContract {
    interface Presenter extends BasePresenter<View> {
        void loadHistory();
    }

    interface View extends BaseView {
        void displayHistoryItems(@NonNull List<TripPoint> tripPoints);

        void showEmptyHistoryAlert();
    }
}