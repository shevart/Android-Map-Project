package com.shevart.google_map.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.shevart.google_map.R;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.models.google_route.GoogleMapRouteStep;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MapUtils {
    private static final LatLng UKRAINE = new LatLng(48.379433, 31.165579999999977);

    public static MarkerOptions createMarkerOptions(@NonNull LatLng mapPoint,
                                                    @NonNull String name, @DrawableRes int iconId) {
        return new MarkerOptions()
                .position(mapPoint)
                .title(name)
                .icon(BitmapDescriptorFactory.fromResource(iconId));
    }

    public static MarkerOptions createMarkerOptionsCurrentLocation(@NonNull LatLng mapPoint, @NonNull String name) {
        return new MarkerOptions()
                .position(mapPoint)
                .title(name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location_black_24dp));
    }

    public static void showFromToPlacesOnTheMap(@NonNull Context context,
                                                @NonNull GoogleMap googleMap,
                                                @NonNull TripPoint startTripPoint,
                                                @NonNull TripPoint finishTripPoint) {
        googleMap.clear();
        LatLngBounds bounds = createLatLngBoundsByPoints(startTripPoint.getLatLng(), finishTripPoint.getLatLng());
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, getPadding(context)));

        googleMap.addMarker(createMarkerOptions(startTripPoint.getLatLng(),
                startTripPoint.getName(), R.drawable.ic_location_on_black_24dp));
        googleMap.addMarker(createMarkerOptions(finishTripPoint.getLatLng(),
                finishTripPoint.getName(), R.drawable.ic_location_on_black_24dp));
    }

    private static int getPadding(@NonNull Context context) {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        return (int) (width * 0.15);
    }

    public static LatLngBounds createLatLngBoundsByPoints(LatLng... points) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : points) {
            builder.include(latLng);
        }
        return builder.build();
    }

    public static void showUkraineOnTheMap(@NonNull GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(UKRAINE));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(4.7f));
    }

    public static void drawTripRoute(@NonNull Context context,
                                     @NonNull GoogleMap googleMap,
                                     @NonNull PolylineOptions polylineOptions) {

        polylineOptions.color(UiUtil.getColor(context, R.color.yellow));
        polylineOptions.width(polylineOptions.getWidth() * 1.4f);
        googleMap.addPolyline(polylineOptions);
    }

    public static List<LatLng> getPointsFromEncodedPolyline(@NonNull String encodedPolyline) {
        ArrayList<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encodedPolyline.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encodedPolyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encodedPolyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public static String latLngToString(@NonNull LatLng latLng) {
        return latLng.latitude + "," + latLng.longitude;
    }

    public static PolylineOptions createPolyline(@NonNull List<GoogleMapRouteStep> steps) {
        PolylineOptions polylineOptions = new PolylineOptions();
        for (GoogleMapRouteStep step : steps) {
            List<LatLng> stepPoints = MapUtils.getPointsFromEncodedPolyline(step.getPolyline().getPoints());
            for (LatLng latLng : stepPoints) {
                polylineOptions.add(latLng);
            }
        }
        return polylineOptions;
    }
}

