package com.shevart.google_map.ui.screens.select_last_place;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shevart.google_map.R;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.ui.base.AbsMVPActivity;
import com.shevart.google_map.util.BundleUtils;

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
        setTitle(getString(R.string.history));

        vwEmptyHistory = findViewById(R.id.llEmptyHistory);
        adapter = new TripPointsHistoryRVAdapter(this);
        RecyclerView rvHistory = findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(adapter);

        getPresenter().loadHistory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionClearHistory:
                askClearHistory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        Intent result = new Intent();
        BundleUtils.setTripPoint(result, tripPoint);
        setResult(RESULT_OK, result);
        finish();
    }

    private void askClearHistory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder
                .setTitle(R.string.attention)
                .setMessage("Очистить историю поиска?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getPresenter().clearHistory();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(true)
                .show();

    }
}