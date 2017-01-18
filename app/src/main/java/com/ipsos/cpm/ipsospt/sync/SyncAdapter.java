package com.ipsos.cpm.ipsospt.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.ipsos.cpm.ipsospt.data.PTContract;


public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private ContentResolver _contentResolver;

    private static final String[] FAM_PROJECTION = new String[] {
            PTContract.Fam.COLUMN_COUNTRY_CODE,
            PTContract.Fam.COLUMN_FAM_CODE,
            PTContract.Fam.COLUMN_FAM_NAME,
            PTContract.Fam.COLUMN_REG_BEG,
            PTContract.Fam.COLUMN_DISTRICT,
            PTContract.Fam.COLUMN_PROVINCE,
            PTContract.Fam.COLUMN_NEIGHBORHOOD,
            PTContract.Fam.COLUMN_ADDRESS,
            PTContract.Fam.COLUMN_LANDLINE,
            PTContract.Fam.COLUMN_WORKLINE,
            PTContract.Fam.COLUMN_CELLULAR,
            PTContract.Fam.COLUMN_FLD_CODE,
            PTContract.Fam.COLUMN_VISIT_DAY,
            PTContract.Fam.COLUMN_AVP,
            PTContract.Fam.COLUMN_ALK,
            PTContract.Fam.COLUMN_SP,
            PTContract.Fam.COLUMN_EK_ALK,
            PTContract.Fam.COLUMN_BABY,
            PTContract.Fam.COLUMN_POINT
    };
    private static final int FAM_COL_COUNTRY_CODE = 0;
    private static final int FAM_COL_FAM_CODE = 1;
    private static final int FAM_COL_FAM_NAME = 2;
    private static final int FAM_COL_REG_BEG = 3;
    private static final int FAM_COL_DISTRICT = 4;
    private static final int FAM_COL_PROVINCE = 5;
    private static final int FAM_COL_NEIGHBORHOOD = 6;
    private static final int FAM_COL_ADDRESS = 7;
    private static final int FAM_COL_LANDLINE = 8;
    private static final int FAM_COL_WORKLINE = 9;
    private static final int FAM_COL_CELLULAR = 10;
    private static final int FAM_COL_FLD_CODE = 11;
    private static final int FAM_COL_VISIT_DAY = 12;
    private static final int FAM_COL_AVP = 13;
    private static final int FAM_COL_ALK = 14;
    private static final int FAM_COL_SP = 15;
    private static final int FAM_COL_EK_ALK = 16;
    private static final int FAM_COL_BABY = 17;
    private static final int FAM_COL_POINT = 18;

    private static final String[] IND_PROJECTION = new String[] {
            PTContract.Ind.COLUMN_COUNTRY_CODE,
            PTContract.Ind.COLUMN_FAM_CODE,
            PTContract.Ind.COLUMN_IND_CODE,
            PTContract.Ind.COLUMN_IND_NAME,
            PTContract.Ind.COLUMN_DOB,
            PTContract.Ind.COLUMN_PHONE,
            PTContract.Ind.COLUMN_PHONE2,
            PTContract.Ind.COLUMN_EMAIL,
            PTContract.Ind.COLUMN_EMAIL2,
            PTContract.Ind.COLUMN_SP,
            PTContract.Ind.COLUMN_ALK
    };
    private static final int IND_COL_COUNTRY_CODE = 0;
    private static final int IND_COL_FAM_CODE = 1;
    private static final int IND_COL_IND_CODE = 2;
    private static final int IND_COL_IND_NAME = 3;
    private static final int IND_COL_DOB = 4;
    private static final int IND_COL_PHONE = 5;
    private static final int IND_COL_PHONE2 = 6;
    private static final int IND_COL_EMAIL = 7;
    private static final int IND_COL_EMAIL2 = 8;
    private static final int IND_COL_SP = 9;
    private static final int IND_COL_ALK = 10;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        _contentResolver = context.getContentResolver();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        _contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

    }
}
