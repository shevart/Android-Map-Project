package com.shevart.google_map.Utils;

import com.google.android.gms.maps.model.LatLng;
import com.shevart.google_map.models.TripPoint;

@SuppressWarnings("WeakerAccess")
public class MockUtil {
    public static final LatLng tripPointMockLatLng = new LatLng(10.5, 100.5);

    public static TripPoint getMockTripPoint() {
        TripPoint tripPoint = new TripPoint();
        tripPoint.setName("Name");
        tripPoint.setAddress("Address");
        tripPoint.setLatLng(tripPointMockLatLng);
        return tripPoint;
    }
}
