package com.ipsos.cpm.ipsospt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ipsos.cpm.ipsospt.LoginActivity;

import java.util.HashMap;

import static com.ipsos.cpm.ipsospt.helper.Constants.IS_LOGIN;
import static com.ipsos.cpm.ipsospt.helper.Constants.KEY_FLD_CODE;
import static com.ipsos.cpm.ipsospt.helper.Constants.KEY_FLD_EMAIL;
import static com.ipsos.cpm.ipsospt.helper.Constants.KEY_FLD_NAME;
import static com.ipsos.cpm.ipsospt.helper.Constants.PREF_NAME;

class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    private int PRIVATE_MODE = 0;

    SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    void createLoginSession(String name, String email, String fldCode){
        editor = pref.edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_FLD_NAME, name);
        editor.putString(KEY_FLD_EMAIL, email);
        editor.putString(KEY_FLD_CODE, fldCode);
        editor.commit();
    }

    HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_FLD_NAME, pref.getString(KEY_FLD_NAME, null));
        user.put(KEY_FLD_EMAIL, pref.getString(KEY_FLD_EMAIL, null));
        user.put(KEY_FLD_CODE, pref.getString(KEY_FLD_CODE, null));

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
        editor.clear();
        editor.commit();
    }

    boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
