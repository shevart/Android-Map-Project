package com.shevart.google_map.data.db;

import android.support.annotation.NonNull;

import com.shevart.google_map.models.TripPoint;

import java.util.List;

public interface DB {
    void save(@NonNull TripPoint tripPoint);

    List<TripPoint> getTripPointsFromHistory(int size);

    void clearTripPointsHistory();
}