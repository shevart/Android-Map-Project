package com.shevart.google_map.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shevart.google_map.data.db.contract.TripPointContract;

class DbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "map_project_db";
    private static final int DB_VERSION = 1;

    DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TripPointContract.Script.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}