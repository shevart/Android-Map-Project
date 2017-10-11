package com.shevart.google_map.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.shevart.google_map.app.App;
import com.shevart.google_map.app.MapProjectApplication;

@SuppressWarnings("unused")
public abstract class AbsActivity extends AppCompatActivity {
    private App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = MapProjectApplication.getApp(this);
    }

    protected App getApp() {
        return app;
    }

    protected void hideActionBar() {
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    @SuppressWarnings("ConstantConditions")
    protected void enableToolbarBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @SuppressWarnings("ConstantConditions")
    protected void disableToolbarBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}