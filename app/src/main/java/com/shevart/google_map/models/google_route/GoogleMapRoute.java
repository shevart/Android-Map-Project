package com.shevart.google_map.models.google_route;


import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class GoogleMapRoute {
    private String copyrights;
    private GoogleRouteBounds bounds;
    private List<GoogleMapLeg> legs;

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public GoogleRouteBounds getBounds() {
        return bounds;
    }

    public void setBounds(GoogleRouteBounds bounds) {
        this.bounds = bounds;
    }

    public List<GoogleMapLeg> getLegs() {
        return legs;
    }

    public void setLegs(List<GoogleMapLeg> legs) {
        this.legs = legs;
    }
}
