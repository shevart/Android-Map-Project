package com.shevart.google_map.data.db.dao;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.shevart.google_map.data.db.contract.TripPointContract;
import com.shevart.google_map.models.TripPoint;

import java.util.ArrayList;
import java.util.List;

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

    public static List<TripPoint> getTripPointsFromHistory(@NonNull SQLiteDatabase database, int size) {
        final Cursor cursor = database.rawQuery(TripPointContract.Script.SELECT_WITH_LIMIT,
                new String[]{String.valueOf(size)});

        if (cursor == null) {
            throw new SQLException();
        }

        final List<TripPoint> tripPoints = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                TripPoint tripPoint = new TripPoint();
                parseTripPoint(cursor, tripPoint);
                tripPoints.add(tripPoint);
            }
        } finally {
            cursor.close();
        }
        return tripPoints;
    }

    private static void insertTripPoint(@NonNull SQLiteStatement statement, @NonNull TripPoint tripPoint) {
        bindString(statement, 1, tripPoint.getName());
        bindString(statement, 2, tripPoint.getAddress());
        statement.bindDouble(3, tripPoint.getLatLng().latitude);
        statement.bindDouble(4, tripPoint.getLatLng().longitude);
        statement.executeInsert();
        statement.clearBindings();
    }

    private static void parseTripPoint(@NonNull Cursor cursor, @NonNull TripPoint tripPoint) {
        tripPoint.setName(parseString(cursor, TripPointContract.NAME));
        tripPoint.setAddress(parseString(cursor, TripPointContract.ADDRESS));
        double lat = parseDouble(cursor, TripPointContract.LATITUDE);
        double lng = parseDouble(cursor, TripPointContract.LONGITUDE);
        tripPoint.setLatLng(new LatLng(lat, lng));
    }
}