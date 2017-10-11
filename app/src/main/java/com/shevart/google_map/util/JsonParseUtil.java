package com.shevart.google_map.util;

import android.support.annotation.NonNull;

import com.shevart.google_map.models.GoogleGeocodedPlace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// TODO: 11.10.17 replace JSON keys to constants
@SuppressWarnings("WeakerAccess")
public class JsonParseUtil {
    public static List<GoogleGeocodedPlace> parseGoogleGeocodedPlaces(@NonNull JSONObject jsonObject) throws JSONException {
        List<GoogleGeocodedPlace> results = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        for (int i = 0; i < jsonArray.length(); i++) {
            results.add(parseGoogleGeocodedPlace(jsonArray.getJSONObject(i)));

        }
        return results;
    }

    public static GoogleGeocodedPlace parseGoogleGeocodedPlace(@NonNull JSONObject jsonObject) throws JSONException {
        GoogleGeocodedPlace place = new GoogleGeocodedPlace();
        place.setFormattedAddress(jsonObject.getString("formatted_address"));
        place.setTypes(parseTypes(jsonObject.getJSONArray("types")));
        return place;
    }

    public static List<String> parseTypes(@NonNull JSONArray jsonArray) throws JSONException {
        List<String> types = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            types.add(jsonArray.getString(i));
        }
        return types;
    }
}
