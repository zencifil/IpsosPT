package com.ipsos.cpm.ipsospt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.ipsos.cpm.ipsospt.LoginActivity;
import com.ipsos.cpm.ipsospt.data.PTContract;

import java.util.HashMap;

import static com.ipsos.cpm.ipsospt.helper.Constants.IS_LOGIN;
import static com.ipsos.cpm.ipsospt.helper.Constants.KEY_FLD_CODE;
import static com.ipsos.cpm.ipsospt.helper.Constants.KEY_FLD_EMAIL;
import static com.ipsos.cpm.ipsospt.helper.Constants.KEY_FLD_NAME;
import static com.ipsos.cpm.ipsospt.helper.Constants.PREF_NAME;

class SessionManager {

    private SharedPreferences _pref;
    private SharedPreferences.Editor _editor;
    private Context _context;

    private int PRIVATE_MODE = 0;

    SessionManager(Context context){
        this._context = context;
        _pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    void createLoginSession(String name, String email, String fldCode){
        _editor = _pref.edit();
        _editor.putBoolean(IS_LOGIN, true);
        _editor.putString(KEY_FLD_NAME, name);
        _editor.putString(KEY_FLD_EMAIL, email);
        _editor.putString(KEY_FLD_CODE, fldCode);
        _editor.commit();
    }

    HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_FLD_NAME, _pref.getString(KEY_FLD_NAME, null));
        user.put(KEY_FLD_EMAIL, _pref.getString(KEY_FLD_EMAIL, null));
        user.put(KEY_FLD_CODE, _pref.getString(KEY_FLD_CODE, null));

        return user;
    }

    void checkLogin() {
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
        else {
            //TODO check for valid token
        }
    }

    void logoutUser(){
        if (_editor != null) {
            _editor.clear();
            _editor.commit();
        }
    }

    boolean isLoggedIn(){
        return _pref.getBoolean(IS_LOGIN, false);
    }

//    String getAuthKey() {
//        Cursor c = _context
//                .getContentResolver()
//                .query(PTContract.UserInfo.CONTENT_URI, new String[] { PTContract.UserInfo.COLUMN_AUTH_KEY },
//                        null, null, null);
//
//        String authKey = "";
//        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
//            do {
//                authKey = c.getString(0);
//            } while (c.moveToNext());
//        }
//        return authKey;
//    }
}
