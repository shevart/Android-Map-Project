package com.shevart.google_map.data.net;

import android.support.annotation.NonNull;

import com.shevart.google_map.data.AsyncDataCallback;
import com.shevart.google_map.util.LogUtil;
import com.shevart.google_map.util.NetUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import javax.net.ssl.HttpsURLConnection;

import static com.shevart.google_map.util.Util.checkNonNull;

class NetRequestExecutorThread extends Thread {
    private NetRequest netRequest;
    private AsyncDataCallback<String> callback;
    private NetBridge netBridge;

    NetRequestExecutorThread(@NonNull NetRequest netRequest,
                             @NonNull AsyncDataCallback<String> callback,
                             @NonNull NetBridge netBridge) {
        checkNonNull(netRequest);
        checkNonNull(callback);
        this.netRequest = netRequest;
        this.callback = callback;
        this.netBridge = netBridge;
    }

    @Override
    public void run() {
        try {
            String result = executeRequest(netRequest);
            netBridge.postResponse(result, callback);
        } catch (IOException e) {
            netBridge.postError(e, callback);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private String executeRequest(@NonNull NetRequest netRequest) throws IOException {
        InputStream stream = null;
        HttpURLConnection connection = null;
        String result = null;
        try {
            connection = initConnection(netRequest);
            connection.connect();
            final int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            stream = connection.getInputStream();
            if (stream != null) {
                result = readStream(stream, 15000);
            }
        } finally {
            if (stream != null)
                stream.close();
            if (connection != null)
                connection.disconnect();
        }
        return result;
    }

    private HttpURLConnection initConnection(@NonNull NetRequest netRequest) throws IOException {
        final HttpURLConnection connection = startConnection(netRequest.getUrl());
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(10000);
        connection.setRequestMethod(NetUtils.convertNetRequestType(netRequest.getType()));
        connection.setDoInput(true);
        return connection;
    }

    private HttpURLConnection startConnection(@NonNull String url) throws IOException {
        LogUtil.e(url);
        if (url.contains("https")) {
            return (HttpsURLConnection) NetUtils.convertString(netRequest.getUrl()).openConnection();
        } else {
            return (HttpURLConnection) NetUtils.convertString(netRequest.getUrl()).openConnection();
        }
    }

    private String readStream(InputStream stream, int maxReadSize)
            throws IOException {
        final Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        final StringBuilder buffer = new StringBuilder();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }
}