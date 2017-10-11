package com.shevart.google_map.data.net;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@SuppressWarnings("WeakerAccess")
public class NetRequest {
    public static final int GET = 1;
    public static final int POST = 2;
    public static final int PUT = 3;
    public static final int DELETE = 4;

    @Retention(SOURCE)
    @IntDef({GET, POST, PUT, DELETE})
    public @interface RequestTypes {
    }

    private String url;
    @RequestTypes
    private int type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @RequestTypes
    public int getType() {
        return type;
    }

    public void setType(@RequestTypes int type) {
        this.type = type;
    }
}