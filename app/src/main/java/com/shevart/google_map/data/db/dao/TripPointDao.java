package com.shevart.google_map.data.db.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;

import com.shevart.google_map.data.db.contract.TripPointContract;
import com.shevart.google_map.models.TripPoint;

public class TripPointDao extends BaseDao {
    private TripPointDao() {
    }

    public static void save(@NonNull SQLiteDatabase database, @NonNull TripPoint tripPoint) {
        final SQLiteStatement statement = database.compileStatement(TripPointContract.Script.INSERT);
        try {
            database.beginTransaction();
            insertTripPoint(statement, tripPoint);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    private static void insertTripPoint(@NonNull SQLiteStatement statement, @NonNull TripPoint tripPoint) {
        statement.bindString(1, tripPoint.getName());
        statement.bindString(2, tripPoint.getAddress());
        statement.bindDouble(3, tripPoint.getLatLng().latitude);
        statement.bindDouble(4, tripPoint.getLatLng().longitude);
        statement.executeInsert();
        statement.clearBindings();
    }
}