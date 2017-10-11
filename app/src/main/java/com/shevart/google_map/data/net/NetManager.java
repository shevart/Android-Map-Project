package com.shevart.google_map.data.net;

import android.support.annotation.NonNull;

import com.shevart.google_map.data.AsyncDataCallback;
import com.shevart.google_map.util.LogUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("unused")
public class NetManager {
    private NetBridge netBridge;
    private ExecutorService executorService;

    public NetManager() {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        netBridge = new NetBridge();
    }

    public void test() {
        NetRequest netRequest = new NetRequest();
        netRequest.setType(NetRequest.GET);
        netRequest.setUrl("https://developer.android.com/training/basics/network-ops/connecting.html");

        AsyncDataCallback<String> callback = new AsyncDataCallback<String>() {
            @Override
            public void onResult(@NonNull String data) {
                LogUtil.e("Response is : " + data);
            }

            @Override
            public void onError(@NonNull Exception e) {
                LogUtil.e(e);
            }
        };

        executorService.execute(new NetRequestThread(netRequest, callback, netBridge));
    }
}