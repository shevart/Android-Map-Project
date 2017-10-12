package com.shevart.google_map.models.google_route;

@SuppressWarnings("unused")
class GoogleRouteBounds {
    private LatLongPoint northeast;
    private LatLongPoint southwest;

    public LatLongPoint getNortheast() {
        return northeast;
    }

    public void setNortheast(LatLongPoint northeast) {
        this.northeast = northeast;
    }

    public LatLongPoint getSouthwest() {
        return southwest;
    }

    public void setSouthwest(LatLongPoint southwest) {
        this.southwest = southwest;
    }
}
