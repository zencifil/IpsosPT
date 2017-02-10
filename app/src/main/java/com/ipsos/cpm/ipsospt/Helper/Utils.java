package com.ipsos.cpm.ipsospt.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import com.ipsos.cpm.ipsospt.R;
import com.ipsos.cpm.ipsospt.sync.SyncAdapter;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

import static com.ipsos.cpm.ipsospt.helper.Constants.KEY_SYNC_STATUS;
import static com.ipsos.cpm.ipsospt.sync.SyncAdapter.SYNC_STATUS_UNKNOWN;

/**
 * Created by zencifil on 14/12/2016.
 */

public class Utils {

    public static int getTodayIndex() {
        Calendar c = Calendar.getInstance();
        String day = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        int today;
        switch (day) {
            case "Monday":
                today = 1;
                break;
            case "Tuesday":
                today = 2;
                break;
            case "Wednesday":
                today = 3;
                break;
            case "Thursday":
                today = 4;
                break;
            case "Friday":
                today = 5;
                break;
            case "Saturday":
                today = 6;
                break;
            case "Sunday":
                today = 7;
                break;
            default:
                today = 0;
                break;
        }

        return today;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isAvailable() &&
                connectivityManager.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    @SuppressWarnings("ResourceType")
    public static @SyncAdapter.SyncStatus
    int getSyncStatus(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getInt(KEY_SYNC_STATUS, SYNC_STATUS_UNKNOWN);
    }

    public static String getLastSyncStatus(Context c) {
        String message = "";
        @SyncAdapter.SyncStatus int syncStatus = Utils.getSyncStatus(c);
        switch (syncStatus) {
            case SyncAdapter.SYNC_STATUS_START:
                message = c.getString(R.string.sync_status_start);
                break;
            case SyncAdapter.SYNC_STATUS_END:
                message = c.getString(R.string.sync_status_end);
                break;
            case SyncAdapter.SYNC_STATUS_OK:
                message = c.getString(R.string.sync_status_ok);
                break;
            case SyncAdapter.SYNC_STATUS_UNAUTHORIZED:
                message = c.getString(R.string.sync_status_unauthorized);
                break;
            case SyncAdapter.SYNC_STATUS_SERVER_DOWN:
                message = c.getString(R.string.sync_status_server_down);
                break;
            case SyncAdapter.SYNC_STATUS_UNKNOWN:
            default:
                message = c.getString(R.string.sync_status_unknown);
        }
        return message;
    }
}
