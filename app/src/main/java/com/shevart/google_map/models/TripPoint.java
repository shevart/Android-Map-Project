package com.shevart.google_map.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TripPoint implements Parcelable {
    private String name;
    private LatLng latLng;
    private String address;

    public TripPoint() {
    }

    public String getName() {
        return name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.latLng, flags);
        dest.writeString(this.address);
    }

    protected TripPoint(Parcel in) {
        this.name = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        int tmpPointType = in.readInt();
        this.address = in.readString();
    }

    public static final Creator<TripPoint> CREATOR = new Creator<TripPoint>() {
        @Override
        public TripPoint createFromParcel(Parcel source) {
            return new TripPoint(source);
        }

        @Override
        public TripPoint[] newArray(int size) {
            return new TripPoint[size];
        }
    };
}
