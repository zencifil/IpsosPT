package com.ipsos.cpm.ipsospt.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.util.Log;

import com.ipsos.cpm.ipsospt.IpsosPTApplication;
import com.ipsos.cpm.ipsospt.R;
import com.ipsos.cpm.ipsospt.data.PTContract;
import com.ipsos.cpm.ipsospt.helper.Constants;
import com.ipsos.cpm.ipsospt.model.Fam;
import com.ipsos.cpm.ipsospt.model.Ind;
import com.ipsos.cpm.ipsospt.model.JsonParser;
import com.ipsos.cpm.ipsospt.model.Panel;
import com.ipsos.cpm.ipsospt.model.PanelWeek;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import static com.ipsos.cpm.ipsospt.helper.Constants.KEY_SYNC_STATUS;
import static com.ipsos.cpm.ipsospt.helper.Constants.SYNC_INTERVAL;

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private final String LOG_TAG = SyncAdapter.class.getSimpleName();
    private ContentResolver _contentResolver;

    public static final int SYNC_STATUS_START = -1;
    public static final int SYNC_STATUS_END = 10;
    public static final int SYNC_STATUS_OK = 0;
    public static final int SYNC_STATUS_NO_DATA = 1;
    public static final int SYNC_STATUS_SERVER_DOWN = 2;
    public static final int SYNC_STATUS_SERVER_INVALID = 3;
    public static final int SYNC_STATUS_NO_USER_DATA = 4;
    public static final int SYNC_STATUS_UNKNOWN = 5;
    public static final int SYNC_STATUS_UNAUTHORIZED = 6;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef( { SYNC_STATUS_START, SYNC_STATUS_OK, SYNC_STATUS_NO_DATA, SYNC_STATUS_SERVER_DOWN,
            SYNC_STATUS_SERVER_INVALID, SYNC_STATUS_UNKNOWN, SYNC_STATUS_NO_USER_DATA,
            SYNC_STATUS_UNAUTHORIZED, SYNC_STATUS_END } )
    public @interface SyncStatus {}

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        _contentResolver = context.getContentResolver();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        _contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s,
                              ContentProviderClient contentProviderClient,
                              SyncResult syncResult) {
        Log.d(LOG_TAG, "Sync started");
        long famInsertCount, indInsertCount, panelInsertCount, panelWeekInsertCount;
        long famDeleteCount, indDeleteCount, panelDeleteCount, panelWeekDeleteCount;
        setSyncStatus(getContext(), SYNC_STATUS_START);
        syncFam(syncResult);
        famInsertCount = syncResult.stats.numInserts;
        famDeleteCount = syncResult.stats.numDeletes;
        syncInd(syncResult);
        indInsertCount = syncResult.stats.numInserts - famInsertCount;
        indDeleteCount = syncResult.stats.numDeletes - famDeleteCount;
        syncPanel(syncResult);
        panelInsertCount = syncResult.stats.numInserts - famInsertCount - indInsertCount;
        panelDeleteCount = syncResult.stats.numDeletes - famDeleteCount - indDeleteCount;
        syncPanelWeek(syncResult);
        panelWeekInsertCount = syncResult.stats.numInserts - famInsertCount - indInsertCount - panelInsertCount;
        panelWeekDeleteCount = syncResult.stats.numDeletes - famDeleteCount - indDeleteCount - panelDeleteCount;
        Log.d(LOG_TAG, "Update numbers:\n");
        Log.d(LOG_TAG, "Fam Delete: " + famDeleteCount + "\n");
        Log.d(LOG_TAG, "Fam Insert: " + famInsertCount + "\n");
        Log.d(LOG_TAG, "Ind Delete: " + indDeleteCount + "\n");
        Log.d(LOG_TAG, "Ind Insert: " + indInsertCount + "\n");
        Log.d(LOG_TAG, "Panel Delete: " + panelDeleteCount + "\n");
        Log.d(LOG_TAG, "Panel Insert: " + panelInsertCount + "\n");
        Log.d(LOG_TAG, "Panel Week Delete: " + panelWeekDeleteCount + "\n");
        Log.d(LOG_TAG, "Panel Week Insert: " + panelWeekInsertCount + "\n");
        setSyncStatus(getContext(), SYNC_STATUS_END);
    }

    private void syncFam(final SyncResult syncResult) {
        if (uploadFam())
            downloadFam(syncResult);
    }

    private void syncInd(final SyncResult syncResult) {
        if (uploadInd())
            downloadInd(syncResult);
    }

    private void syncPanel(final SyncResult syncResult) {
        downloadPanel(syncResult);
    }

    private void syncPanelWeek(final SyncResult syncResult) {
        if (uploadPanelWeek())
            downloadPanelWeek(syncResult);
    }

    private boolean uploadFam() {
        return true;
    }

    private boolean uploadInd() {
        return true;
    }

    private boolean uploadPanelWeek() {
        //TODO fill
        return true;
    }

    private void downloadFam(final SyncResult syncResult) {
        String urlStr = Constants.BASE_URL + Constants.API_GET_FAM;

        try {
            JSONArray famArray = getJsonArrayFromAPI(urlStr);
            if (famArray == null)
                return;
            updateFam(famArray, syncResult);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    private void downloadInd(final SyncResult syncResult) {
        String urlStr = Constants.BASE_URL + Constants.API_GET_IND;
        try {
            JSONArray indArray = getJsonArrayFromAPI(urlStr);
            if (indArray == null)
                return;
            updateInd(indArray, syncResult);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    private void downloadPanel(final SyncResult syncResult) {
        String urlStr = Constants.BASE_URL + Constants.API_GET_PANEL;
        try {
            JSONArray panelArray = getJsonArrayFromAPI(urlStr);
            if (panelArray == null)
                return;
            updatePanel(panelArray, syncResult);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    private void downloadPanelWeek(final SyncResult syncResult) {
        String urlStr = Constants.BASE_URL + Constants.API_GET_PANEL_WEEK;
        try {
            JSONArray pwArray = getJsonArrayFromAPI(urlStr);
            if (pwArray == null)
                return;
            updatePanelWeek(pwArray, syncResult);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    private JSONArray getJsonArrayFromAPI(String urlStr) {
        //TODO LOG exception detail

        HttpsURLConnection urlConnection = null;
        HashMap<String, String> userDetails = IpsosPTApplication.getInstance().getUserDetails();
        if (userDetails == null) {
            setSyncStatus(getContext(), SYNC_STATUS_NO_USER_DATA);
            return null;
        }

        String token = IpsosPTApplication.getInstance().getAuthKey();
        if (token == null || token.equals("")) {
            setSyncStatus(getContext(), SYNC_STATUS_NO_USER_DATA);
            return null;
        }

        BufferedReader reader;

        try {
            URL url = new URL(urlStr);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "bearer " + token);
            urlConnection.setDoInput(true);

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null)
                return null;
            StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0)
                return null;

            return new JSONArray(buffer.toString());
        }
        catch (IOException ex) {
            setSyncStatus(getContext(), SYNC_STATUS_SERVER_DOWN);
            return null;
        }
        catch (JSONException ex) {
            setSyncStatus(getContext(), SYNC_STATUS_SERVER_INVALID);
            return null;
        }
        catch (Exception ex) {
            setSyncStatus(getContext(), SYNC_STATUS_UNKNOWN);
            return null;
        }
        finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    private void updateFam(JSONArray famArray, final SyncResult syncResult) {
        try {
            ArrayList<ContentProviderOperation> batch = new ArrayList<>();
            Uri uri = PTContract.Fam.CONTENT_URI;
            //get remote data
            for (int i = 0; i < famArray.length(); i++) {
                JSONObject fam = famArray.getJSONObject(i);
                Fam f = JsonParser.parseFam(fam);
                if (f == null) {
                    Log.i(LOG_TAG, "Non parsable json found: " + fam);
                    continue;
                }

                batch.add(ContentProviderOperation.newInsert(uri)
                        .withValue(PTContract.Fam.COLUMN_COUNTRY_CODE, f.CountryCode)
                        .withValue(PTContract.Fam.COLUMN_FAM_CODE, f.FamCode)
                        .withValue(PTContract.Fam.COLUMN_FAM_NAME, f.FamName)
                        .withValue(PTContract.Fam.COLUMN_REG_BEG, f.RegBeg)
                        .withValue(PTContract.Fam.COLUMN_DISTRICT, f.District)
                        .withValue(PTContract.Fam.COLUMN_PROVINCE, f.Province)
                        .withValue(PTContract.Fam.COLUMN_NEIGHBORHOOD, f.Neighborhood)
                        .withValue(PTContract.Fam.COLUMN_ADDRESS, f.Address)
                        .withValue(PTContract.Fam.COLUMN_LANDLINE, f.Landline)
                        .withValue(PTContract.Fam.COLUMN_WORKLINE, f.Workline)
                        .withValue(PTContract.Fam.COLUMN_CELLULAR, f.Cellular)
                        .withValue(PTContract.Fam.COLUMN_FLD_CODE, f.FldCode)
                        .withValue(PTContract.Fam.COLUMN_VISIT_DAY, f.VisitDay)
                        .withValue(PTContract.Fam.COLUMN_AVP, f.AVP)
                        .withValue(PTContract.Fam.COLUMN_ALK, f.ALK)
                        .withValue(PTContract.Fam.COLUMN_SP, f.SP)
                        .withValue(PTContract.Fam.COLUMN_EK_ALK, f.EK_ALK)
                        .withValue(PTContract.Fam.COLUMN_BABY, f.BABY)
                        .withValue(PTContract.Fam.COLUMN_POINT, f.Point)
                        .build());
                syncResult.stats.numInserts++;
            }

            //delete local data
            syncResult.stats.numDeletes += _contentResolver.delete(uri, null, null);

            //insert new ones and notify
            _contentResolver.applyBatch(PTContract.CONTENT_AUTHORITY, batch);
            _contentResolver.notifyChange(uri, null, false);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    private void updateInd(JSONArray indArray, final SyncResult syncResult) {
        try {
            ArrayList<ContentProviderOperation> batch = new ArrayList<>();
            Uri uri = PTContract.Ind.CONTENT_URI;
            for (int j = 0; j < indArray.length(); j++) {
                JSONObject ind = indArray.getJSONObject(j);
                Ind i = JsonParser.parseInd(ind);
                if (i == null) {
                    Log.i(LOG_TAG, "Non parsable json found: " + ind);
                    continue;
                }

                batch.add(ContentProviderOperation.newInsert(uri)
                        .withValue(PTContract.Ind.COLUMN_COUNTRY_CODE, i.CountryCode)
                        .withValue(PTContract.Ind.COLUMN_FAM_CODE, i.FamCode)
                        .withValue(PTContract.Ind.COLUMN_IND_CODE, i.IndCode)
                        .withValue(PTContract.Ind.COLUMN_IND_NAME, i.IndName)
                        .withValue(PTContract.Ind.COLUMN_DOB, i.DoB)
                        .withValue(PTContract.Ind.COLUMN_PHONE, i.Phone)
                        .withValue(PTContract.Ind.COLUMN_PHONE2, i.Phone2)
                        .withValue(PTContract.Ind.COLUMN_EMAIL, i.Email)
                        .withValue(PTContract.Ind.COLUMN_EMAIL2, i.Email2)
                        .withValue(PTContract.Ind.COLUMN_SP, i.SP)
                        .withValue(PTContract.Ind.COLUMN_ALK, i.ALK)
                        .withValue(PTContract.Ind.COLUMN_ISUSER, i.IsUser)
                        .withValue(PTContract.Ind.COLUMN_ACTIVE, i.Active)
                        .build());
                syncResult.stats.numInserts++;
            }

            syncResult.stats.numDeletes += _contentResolver.delete(uri, null, null);

            _contentResolver.applyBatch(PTContract.CONTENT_AUTHORITY, batch);
            _contentResolver.notifyChange(uri, null, false);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    private void updatePanel(JSONArray panelArray, final SyncResult syncResult) {
        try {
            ArrayList<ContentProviderOperation> batch = new ArrayList<>();
            Uri uri = PTContract.Panel.CONTENT_URI;
            for (int i = 0; i < panelArray.length(); i++) {
                JSONObject panel = panelArray.getJSONObject(i);
                Panel p = JsonParser.parsePanel(panel);
                if (p == null) {
                    Log.i(LOG_TAG, "Non parsable json found: " + panel);
                    continue;
                }

                batch.add(ContentProviderOperation.newInsert(uri)
                        .withValue(PTContract.Panel.COLUMN_COUNTRY_CODE, p.CountryCode)
                        .withValue(PTContract.Panel.COLUMN_FLD_CODE, p.FldCode)
                        .withValue(PTContract.Panel.COLUMN_PANEL_TYPE, p.PanelType)
                        .withValue(PTContract.Panel.COLUMN_FAM_CODE, p.FamCode)
                        .withValue(PTContract.Panel.COLUMN_IND_CODE, p.IndCode)
                        .withValue(PTContract.Panel.COLUMN_WEEK_CODE, p.WeekCode)
                        .withValue(PTContract.Panel.COLUMN_WEEK_CHECK, p.WeekCheck)
                        .build());
                syncResult.stats.numInserts++;
            }

            syncResult.stats.numDeletes += _contentResolver.delete(uri, null, null);

            _contentResolver.applyBatch(PTContract.CONTENT_AUTHORITY, batch);
            _contentResolver.notifyChange(uri, null, false);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    private void updatePanelWeek(JSONArray pwArray, final SyncResult syncResult) {
        try {
            ArrayList<ContentProviderOperation> batch = new ArrayList<>();
            Uri uri = PTContract.PanelWeek.CONTENT_URI;
            for (int i = 0; i < pwArray.length(); i++) {
                JSONObject panelWeek = pwArray.getJSONObject(i);
                PanelWeek pw = JsonParser.parsePanelWeek(panelWeek);
                if (pw == null) {
                    Log.i(LOG_TAG, "Non parsable json found: " + panelWeek);
                    continue;
                }

                batch.add(ContentProviderOperation.newInsert(uri)
                        .withValue(PTContract.PanelWeek.COLUMN_COUNTRY_CODE, pw.CountryCode)
                        .withValue(PTContract.PanelWeek.COLUMN_PANEL_TYPE, pw.PanelType)
                        .withValue(PTContract.PanelWeek.COLUMN_WEEK_CODE, pw.WeekCode)
                        .withValue(PTContract.PanelWeek.COLUMN_WEEK_DESC, pw.WeekDesc)
                        .withValue(PTContract.PanelWeek.COLUMN_START_DATE, pw.StartDate)
                        .withValue(PTContract.PanelWeek.COLUMN_END_DATE, pw.EndDate)
                        .withValue(PTContract.PanelWeek.COLUMN_ACTIVE, pw.Active)
                        .build());
                syncResult.stats.numInserts++;
            }

            syncResult.stats.numDeletes += _contentResolver.delete(uri, null, null);

            _contentResolver.applyBatch(PTContract.CONTENT_AUTHORITY, batch);
            _contentResolver.notifyChange(uri, null, false);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    private static void setSyncStatus(Context c, @SyncStatus int syncStatus) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt(KEY_SYNC_STATUS, syncStatus);
        spe.commit();
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        // we can enable inexact timers in our periodic sync
        SyncRequest request = new SyncRequest.Builder().
                syncPeriodic(syncInterval, flexTime).
                setSyncAdapter(account, authority).
                setExtras(new Bundle()).build();
        ContentResolver.requestSync(request);
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(context.getString(R.string.sync_account_name),
                context.getString(R.string.sync_account_type));

        String authority = context.getString(R.string.content_authority);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            ContentResolver.setIsSyncable(newAccount, authority, 1);
            ContentResolver.setSyncAutomatically(newAccount, authority, true);
            ContentResolver.addPeriodicSync(newAccount, authority, new Bundle(), SYNC_INTERVAL);
        }

        return newAccount;
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}
