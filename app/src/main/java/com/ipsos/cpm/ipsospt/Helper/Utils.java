package com.ipsos.cpm.ipsospt.Helper;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Intent.ACTION_SENDTO;
import static android.content.Intent.EXTRA_SUBJECT;
import static android.content.Intent.EXTRA_TEXT;

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

}
