package com.shevart.google_map.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.shevart.google_map.data.db.dao.TripPointDao;
import com.shevart.google_map.models.TripPoint;

import java.util.List;

import static com.shevart.google_map.util.Util.checkNonNull;

public class DatabaseManager implements DB {
    private SQLiteDatabase database;

    public DatabaseManager(@NonNull Context context) {
        DbOpenHelper helper = new DbOpenHelper(context);
        database = helper.getWritableDatabase();
    }

    @Override
    public void save(@NonNull TripPoint tripPoint) {
        checkNonNull(tripPoint);
        TripPointDao.save(database, tripPoint);
    }

    @Override
    public List<TripPoint> getTripPointsFromHistory(int size) {
        return TripPointDao.getTripPointsFromHistory(database, size);
    }

    @Override
    public void clearTripPointsHistory() {
        TripPointDao.clearTripPointsData(database);
    }
}