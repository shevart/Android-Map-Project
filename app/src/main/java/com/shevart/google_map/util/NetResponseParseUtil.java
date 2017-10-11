package com.shevart.google_map.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.shevart.google_map.models.GoogleGeocodedPlace;
import com.shevart.google_map.models.TripPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NetResponseParseUtil {
    public static TripPoint stringToTripPoint(@NonNull String response) throws JSONException {
        List<GoogleGeocodedPlace> places = JsonParseUtil.parseGoogleGeocodedPlaces(new JSONObject(response));
        TripPoint tripPoint = new TripPoint();
        tripPoint.setAddress(findPointAddress(places));
        return tripPoint;
    }

    @Nullable
    private static String findPointAddress(@NonNull List<GoogleGeocodedPlace> places) {
        for (GoogleGeocodedPlace place : places) {
            for (String type : place.getTypes()) {
                if (type.equals(GoogleGeocodedPlace.TYPE_STREET_ADDRESS)) {
                    return place.getFormattedAddress();
                }
            }
        }
        return null;
    }
}