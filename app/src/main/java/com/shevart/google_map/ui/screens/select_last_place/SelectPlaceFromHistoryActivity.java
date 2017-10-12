package com.shevart.google_map.ui.screens.select_last_place;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.shevart.google_map.R;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.ui.base.AbsMVPActivity;

import java.util.List;

public class SelectPlaceFromHistoryActivity extends AbsMVPActivity<SelectLastPlaceContract.Presenter, SelectLastPlaceContract.View> implements SelectLastPlaceContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_place_from_history);
    }

    @Override
    protected SelectLastPlaceContract.Presenter obtainPresenter() {
        return new SelectLastPlacePresenter(getApp().getDB());
    }

    @Override
    protected SelectLastPlaceContract.View obtainView() {
        return this;
    }

    @Override
    public void showProgress() {
        throw new UnsupportedOperationException("Handle it!");
    }

    @Override
    public void hideProgress() {
        throw new UnsupportedOperationException("Handle it!");
    }

    @Override
    public void displayHistoryItems(@NonNull List<TripPoint> tripPoints) {

    }

    @Override
    public void showEmptyHistoryAlert() {

    }
}
