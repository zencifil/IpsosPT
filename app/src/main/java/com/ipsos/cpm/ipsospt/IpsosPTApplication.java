package com.ipsos.cpm.ipsospt;

import android.app.Application;

import com.ipsos.cpm.ipsospt.helper.ConnectivityReceiver;

public class IpsosPTApplication extends Application {
    private static IpsosPTApplication _instance;

    @Override
    public void onCreate() {
        super.onCreate();

        _instance = this;
    }

    public static synchronized IpsosPTApplication getInstance() {
        return _instance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
