package com.shevart.google_map.ui.screens.select_last_place;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shevart.google_map.R;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.ui.base.AbsMVPActivity;

import java.util.List;

public class SelectPlaceFromHistoryActivity extends AbsMVPActivity<SelectLastPlaceContract.Presenter,
        SelectLastPlaceContract.View> implements SelectLastPlaceContract.View, TripPointsHistoryRVAdapter.TripPointSelectListener {
    private TripPointsHistoryRVAdapter adapter;
    private View vwEmptyHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_place_from_history);
        enableToolbarBackButton();

        vwEmptyHistory = findViewById(R.id.llEmptyHistory);
        adapter = new TripPointsHistoryRVAdapter(this);
        RecyclerView rvHistory = findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(adapter);

        getPresenter().loadHistory();
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
        adapter.updateItem(tripPoints);
    }

    @Override
    public void showEmptyHistoryAlert() {
        vwEmptyHistory.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTripPointSelected(@NonNull TripPoint tripPoint) {

    }
}