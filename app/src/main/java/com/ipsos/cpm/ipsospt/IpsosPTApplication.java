package com.ipsos.cpm.ipsospt;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.ipsos.cpm.ipsospt.data.PTContract;
import com.ipsos.cpm.ipsospt.helper.ConnectivityReceiver;

import java.util.HashMap;

import static com.ipsos.cpm.ipsospt.helper.Constants.IS_LOGIN;
import static com.ipsos.cpm.ipsospt.helper.Constants.KEY_FLD_CODE;
import static com.ipsos.cpm.ipsospt.helper.Constants.KEY_FLD_EMAIL;
import static com.ipsos.cpm.ipsospt.helper.Constants.KEY_FLD_NAME;
import static com.ipsos.cpm.ipsospt.helper.Constants.PREF_NAME;

public class IpsosPTApplication extends Application {
    private static IpsosPTApplication _instance;
    private SharedPreferences _pref;
    private SharedPreferences.Editor _editor;
    private int PRIVATE_MODE = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        _instance = this;
        _pref = getApplicationContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public static synchronized IpsosPTApplication getInstance() {
        return _instance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public void createLoginSession(String name, String email, String fldCode){
        _editor = _pref.edit();
        _editor.putBoolean(IS_LOGIN, true);
        _editor.putString(KEY_FLD_NAME, name);
        _editor.putString(KEY_FLD_EMAIL, email);
        _editor.putString(KEY_FLD_CODE, fldCode);
        _editor.apply();
        _editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_FLD_NAME, _pref.getString(KEY_FLD_NAME, null));
        user.put(KEY_FLD_EMAIL, _pref.getString(KEY_FLD_EMAIL, null));
        user.put(KEY_FLD_CODE, _pref.getString(KEY_FLD_CODE, null));

        return user;
    }

    public String getAuthKey() {
        Cursor c = getContentResolver()
                .query(PTContract.UserInfo.CONTENT_URI, new String[] { PTContract.UserInfo.COLUMN_AUTH_KEY },
                        null, null, null);

        String authKey = null;
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                authKey = c.getString(0);
            } while (c.moveToNext());
            c.close();
        }
        return authKey;
    }

    public boolean isLoggedIn() {
        String token = getAuthKey();
        if (token == null || token.isEmpty())
            return false;
        else
            return _pref.getBoolean(IS_LOGIN, false);
    }

    public void logout() {
        if (_editor == null)
            _editor = _pref.edit();
        _editor.clear();
        _editor.apply();
        _editor.commit();

        getContentResolver().delete(PTContract.UserInfo.CONTENT_URI, null, null);

        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
