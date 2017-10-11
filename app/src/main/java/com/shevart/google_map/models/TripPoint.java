package com.shevart.google_map.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import static com.shevart.google_map.util.Util.checkNonNull;

@SuppressWarnings("WeakerAccess")
public class TripPoint implements Parcelable {
    private String name;
    private LatLng latLng;
    private TripPointType pointType;

    public TripPoint(@Nullable LatLng latLng, @Nullable String name, @NonNull TripPointType tripPointType) {
        checkNonNull(tripPointType);
        this.latLng = latLng;
        this.name = name;
        this.pointType = tripPointType;
    }

    public String getName() {
        return name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public TripPointType getPointType() {
        return pointType;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum TripPointType {
        MAP_POINT,
        CURRENT_LOCATION
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.latLng, flags);
        dest.writeInt(this.pointType == null ? -1 : this.pointType.ordinal());
    }

    protected TripPoint(Parcel in) {
        this.name = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        int tmpPointType = in.readInt();
        this.pointType = tmpPointType == -1 ? null : TripPointType.values()[tmpPointType];
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
