package com.shevart.google_map.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.shevart.google_map.R;
import com.shevart.google_map.data.db.DB;
import com.shevart.google_map.data.db.DatabaseManager;
import com.shevart.google_map.data.net.Net;
import com.shevart.google_map.data.net.NetManager;

public class MapProjectApplication extends Application implements App, TokenProvider {
    private Net netManager;
    private DB databaseManager;

    @Override
    public void onCreate() {
        super.onCreate();
        netManager = new NetManager(this, this);
        databaseManager = new DatabaseManager(this);
    }

    @Override
    public Net getNet() {
        return netManager;
    }

    @Override
    public DB getDB() {
        return databaseManager;
    }

    @Override
    public String getGooglePlaceWebApiKey() {
        return getString(R.string.google_place_api_key);
    }

    public static App getApp(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }
}