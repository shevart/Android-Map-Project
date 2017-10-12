package com.shevart.google_map.models.google_route;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public class GoogleMapLeg {
    private Distance distance;
    private Duration duration;

    @SerializedName("start_address")
    private String startAddress;
    @SerializedName("start_location")
    private LatLongPoint startLocation;
    @SerializedName("end_address")
    private String endAddress;
    @SerializedName("end_location")
    private LatLongPoint endLocation;
    private List<GoogleMapRouteStep> steps;

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

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public LatLongPoint getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLongPoint startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public LatLongPoint getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLongPoint endLocation) {
        this.endLocation = endLocation;
    }

    public List<GoogleMapRouteStep> getSteps() {
        return steps;
    }

    public void setSteps(List<GoogleMapRouteStep> steps) {
        this.steps = steps;
    }
}
