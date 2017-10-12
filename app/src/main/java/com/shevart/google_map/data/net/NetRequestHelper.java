package com.shevart.google_map.data.net;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

class NetRequestHelper {
    private static final String PLACE_BY_COORDINATES_URL_PATTERN = "https://maps.googleapis.com/maps/api/geocode/json?key=%s&latlng=%s&language=uk";
    private static final String GET_ROUTE_BY_COORDINATES_URL_PATTERN = "https://maps.googleapis.com/maps/api/directions/json?key=%s&origin=%s&destination=%s&language=uk";

    static NetRequest forPlaceByCoordinates(@NonNull String apiKey, @NonNull LatLng latLng) {
        NetRequest netRequest = new NetRequest();
        netRequest.setType(NetRequest.GET);
        netRequest.setUrl(String.format(Locale.ENGLISH, PLACE_BY_COORDINATES_URL_PATTERN, apiKey, latLng.latitude + "," + latLng.longitude));
        return netRequest;
    }

    static NetRequest forRouteByCoordinates(@NonNull String apiKey, @NonNull String startLatLng,
                                            @NonNull String endLatLng) {
        NetRequest netRequest = new NetRequest();
        netRequest.setType(NetRequest.GET);
        netRequest.setUrl(String.format(Locale.ENGLISH, GET_ROUTE_BY_COORDINATES_URL_PATTERN,
                apiKey, startLatLng, endLatLng));
        return netRequest;
    }
}
