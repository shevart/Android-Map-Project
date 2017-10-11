package com.shevart.google_map.models;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class GoogleGeocodedPlace {
    public static final String TYPE_STREET_ADDRESS = "street_address";
    private String formattedAddress;
    private List<String> types;

    public static String getTypeStreetAddress() {
        return TYPE_STREET_ADDRESS;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formatted_address) {
        this.formattedAddress = formatted_address;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}