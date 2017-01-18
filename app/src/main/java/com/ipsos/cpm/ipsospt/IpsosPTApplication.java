package com.ipsos.cpm.ipsospt;

import android.app.Application;
import android.database.Cursor;

import com.ipsos.cpm.ipsospt.data.PTContract;
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

    public String getAuthKey() {
        Cursor c = getContentResolver()
                .query(PTContract.UserInfo.CONTENT_URI, new String[] { PTContract.UserInfo.COLUMN_AUTH_KEY },
                        null, null, null);

        String authKey = "";
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                authKey = c.getString(0);
            } while (c.moveToNext());
            c.close();
        }
        return authKey;
    }
}
