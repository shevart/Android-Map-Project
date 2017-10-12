package com.shevart.google_map.models.google_route;

import com.google.gson.annotations.SerializedName;

public class GoogleMapRouteStep {
    private Distance distance;
    private Duration duration;
    @SerializedName("start_location")
    private LatLongPoint startLocation;
    @SerializedName("end_location")
    private LatLongPoint endLocation;
    @SerializedName("travel_mode")
    private String travelMode;
    private GoogleMapEncodedPolyline polyline;

    public GoogleMapEncodedPolyline getPolyline() {
        return polyline;
    }

    public void setPolyline(GoogleMapEncodedPolyline polyline) {
        this.polyline = polyline;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LatLongPoint getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLongPoint startLocation) {
        this.startLocation = startLocation;
    }

    public LatLongPoint getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLongPoint endLocation) {
        this.endLocation = endLocation;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }
}
