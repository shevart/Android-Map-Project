package com.shevart.google_map.data.net.response;

import com.shevart.google_map.models.google_route.GoogleMapRoute;

import java.util.List;

public class GetGoogleMapRouteResponse {
    private String status;
    private List<GoogleMapRoute> routes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GoogleMapRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<GoogleMapRoute> routes) {
        this.routes = routes;
    }
}