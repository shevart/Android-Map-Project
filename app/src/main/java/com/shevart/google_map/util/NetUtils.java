package com.shevart.google_map.util;

import android.support.annotation.NonNull;

import com.shevart.google_map.data.net.NetRequest;

import java.net.MalformedURLException;
import java.net.URL;

public class NetUtils {
    public static URL convertString(@NonNull String s) throws MalformedURLException {
        return new URL(s);
    }

    public static String convertNetRequestType(@NetRequest.RequestTypes int type) {
        switch (type) {
            case NetRequest.GET:
                return "GET";
            case NetRequest.POST:
                return "POST";
            case NetRequest.PUT:
                return "PUT";
            case NetRequest.DELETE:
                return "delete";
            default:
                throw new IllegalArgumentException();
        }
    }
}
