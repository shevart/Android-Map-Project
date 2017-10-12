package com.shevart.google_map.ui.screens.select_last_place;

import android.support.annotation.NonNull;

import com.shevart.google_map.data.db.DB;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.ui.mvp.AbsPresenter;

import java.util.List;

import static com.shevart.google_map.util.Util.checkNonNull;
import static com.shevart.google_map.util.Util.isNullOrEmpty;

class SelectLastPlacePresenter extends AbsPresenter<SelectLastPlaceContract.View>
    implements SelectLastPlaceContract.Presenter {
    private static final int HISTORY_LIMIT = 10;

    private DB db;

    SelectLastPlacePresenter(@NonNull DB db) {
        checkNonNull(db);
        this.db = db;
    }

    @Override
    public void loadHistory() {
        final List<TripPoint> history = db.getTripPointsFromHistory(HISTORY_LIMIT);
        if (!isNullOrEmpty(history)) {
            getView().displayHistoryItems(history);
        } else {
            getView().showEmptyHistoryAlert();
        }
    }
}
