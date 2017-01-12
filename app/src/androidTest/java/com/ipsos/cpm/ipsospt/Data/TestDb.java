package com.ipsos.cpm.ipsospt.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.HashSet;

/**
 * Created by zencifil on 29/11/2016.
 */

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    //first method to call.
    @Override
    public void setUp() {
        deleteDatabase();
    }

    void deleteDatabase() {
        mContext.deleteDatabase(PTDbHelper.DATABASE_NAME);
    }

    public void testCreateDatabase() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<>();
        tableNameHashSet.add(PTContract.Fam.TABLE_NAME);
        tableNameHashSet.add(PTContract.Ind.TABLE_NAME);
        tableNameHashSet.add(PTContract.Fld.TABLE_NAME);
        tableNameHashSet.add(PTContract.Panel.TABLE_NAME);
        tableNameHashSet.add(PTContract.PanelWeek.TABLE_NAME);
        tableNameHashSet.add(PTContract.Log.TABLE_NAME);

        mContext.deleteDatabase(PTDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new PTDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Log.d(LOG_TAG, "db created");
        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly", c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        assertTrue("Error: Your database was created without some table(s).", tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        //FAM00
        c = db.rawQuery("PRAGMA table_info(" + PTContract.Fam.TABLE_NAME + ")", null);
        assertTrue("Error: This means that we were unable to query the database for FAM00 table information.", c.moveToFirst());

        Log.d(LOG_TAG, "checking columns for FAM00 table");
        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> famColumnHashSet = new HashSet<>();
        famColumnHashSet.add(PTContract.Fam._ID);
        famColumnHashSet.add(PTContract.Fam.COLUMN_COUNTRY_CODE);
        famColumnHashSet.add(PTContract.Fam.COLUMN_FAM_CODE);
        famColumnHashSet.add(PTContract.Fam.COLUMN_FAM_NAME);
        famColumnHashSet.add(PTContract.Fam.COLUMN_REG_BEG);
        famColumnHashSet.add(PTContract.Fam.COLUMN_DISTRICT);
        famColumnHashSet.add(PTContract.Fam.COLUMN_PROVINCE);
        famColumnHashSet.add(PTContract.Fam.COLUMN_NEIGHBORHOOD);
        famColumnHashSet.add(PTContract.Fam.COLUMN_ADDRESS);
        famColumnHashSet.add(PTContract.Fam.COLUMN_LANDLINE);
        famColumnHashSet.add(PTContract.Fam.COLUMN_WORKLINE);
        famColumnHashSet.add(PTContract.Fam.COLUMN_CELLULAR);
        famColumnHashSet.add(PTContract.Fam.COLUMN_FLD_CODE);
        famColumnHashSet.add(PTContract.Fam.COLUMN_VISIT_DAY);
        famColumnHashSet.add(PTContract.Fam.COLUMN_AVP);
        famColumnHashSet.add(PTContract.Fam.COLUMN_ALK);
        famColumnHashSet.add(PTContract.Fam.COLUMN_SP);
        famColumnHashSet.add(PTContract.Fam.COLUMN_EK_ALK);
        famColumnHashSet.add(PTContract.Fam.COLUMN_BABY);
        famColumnHashSet.add(PTContract.Fam.COLUMN_POINT);
        famColumnHashSet.add(PTContract.Fam.COLUMN_ISUSER);
        famColumnHashSet.add(PTContract.Fam.COLUMN_ACTIVE);
        famColumnHashSet.add(PTContract.Fam.COLUMN_SYNC);
        famColumnHashSet.add(PTContract.Fam.COLUMN_SYNC_DATE);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            famColumnHashSet.remove(c.getString(columnNameIndex));
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required FAM00 columns", famColumnHashSet.isEmpty());


        //IND00
        c = db.rawQuery("PRAGMA table_info(" + PTContract.Ind.TABLE_NAME + ")", null);
        assertTrue("Error: This means that we were unable to query the database for IND00 table information.", c.moveToFirst());

        Log.d(LOG_TAG, "checking columns for FAM00 table");
        final HashSet<String> indColumnHashSet = new HashSet<>();
        indColumnHashSet.add(PTContract.Ind._ID);
        indColumnHashSet.add(PTContract.Ind.COLUMN_COUNTRY_CODE);
        indColumnHashSet.add(PTContract.Ind.COLUMN_FAM_CODE);
        indColumnHashSet.add(PTContract.Ind.COLUMN_IND_CODE);
        indColumnHashSet.add(PTContract.Ind.COLUMN_IND_NAME);
        indColumnHashSet.add(PTContract.Ind.COLUMN_DOB);
        indColumnHashSet.add(PTContract.Ind.COLUMN_PHONE);
        indColumnHashSet.add(PTContract.Ind.COLUMN_PHONE2);
        indColumnHashSet.add(PTContract.Ind.COLUMN_EMAIL);
        indColumnHashSet.add(PTContract.Ind.COLUMN_EMAIL2);
        indColumnHashSet.add(PTContract.Ind.COLUMN_SP);
        indColumnHashSet.add(PTContract.Ind.COLUMN_ALK);
        indColumnHashSet.add(PTContract.Ind.COLUMN_ISUSER);
        indColumnHashSet.add(PTContract.Ind.COLUMN_ACTIVE);
        indColumnHashSet.add(PTContract.Ind.COLUMN_SYNC);
        indColumnHashSet.add(PTContract.Ind.COLUMN_SYNC_DATE);

        columnNameIndex = c.getColumnIndex("name");
        do {
            indColumnHashSet.remove(c.getString(columnNameIndex));
        } while (c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required IND00 columns", indColumnHashSet.isEmpty());

        //FLD00
        c = db.rawQuery("PRAGMA table_info(" + PTContract.Fld.TABLE_NAME + ")", null);
        assertTrue("Error: This means that we were unable to query the database for FLD00 table information.", c.moveToFirst());

        Log.d(LOG_TAG, "checking columns for FLD00 table");
        final HashSet<String> fldColumnHashSet = new HashSet<>();
        fldColumnHashSet.add(PTContract.Fld._ID);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_COUNTRY_CODE);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_FLD_CODE);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_FLD_NAME);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_PROVINCE);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_REGION);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_PHONE);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_PHONE2);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_EMAIL);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_EMAIL2);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_ISUSER);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_ACTIVE);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_SYNC);
        fldColumnHashSet.add(PTContract.Fld.COLUMN_SYNC_DATE);

        columnNameIndex = c.getColumnIndex("name");
        do {
            fldColumnHashSet.remove(c.getString(columnNameIndex));
        } while (c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required FLD00 columns", fldColumnHashSet.isEmpty());

        //PANEL
        c = db.rawQuery("PRAGMA table_info(" + PTContract.Panel.TABLE_NAME + ")", null);
        assertTrue("Error: This means that we were unable to query the database for PANEL table information.", c.moveToFirst());

        Log.d(LOG_TAG, "checking columns for PANEL table");
        final HashSet<String> panelColumnHashSet = new HashSet<>();
        panelColumnHashSet.add(PTContract.Panel._ID);
        panelColumnHashSet.add(PTContract.Panel.COLUMN_COUNTRY_CODE);
        panelColumnHashSet.add(PTContract.Panel.COLUMN_FLD_CODE);
        panelColumnHashSet.add(PTContract.Panel.COLUMN_PANEL_TYPE);
        panelColumnHashSet.add(PTContract.Panel.COLUMN_FAM_CODE);
        panelColumnHashSet.add(PTContract.Panel.COLUMN_IND_CODE);
        panelColumnHashSet.add(PTContract.Panel.COLUMN_WEEK_CODE);
        panelColumnHashSet.add(PTContract.Panel.COLUMN_WEEK_CHECK);
        panelColumnHashSet.add(PTContract.Panel.COLUMN_SYNC);
        panelColumnHashSet.add(PTContract.Panel.COLUMN_SYNC_DATE);

        columnNameIndex = c.getColumnIndex("name");
        do {
            panelColumnHashSet.remove(c.getString(columnNameIndex));
        } while (c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required PANEL columns", panelColumnHashSet.isEmpty());

        //PANEL_WEEK
        c = db.rawQuery("PRAGMA table_info(" + PTContract.PanelWeek.TABLE_NAME + ")", null);
        assertTrue("Error: This means that we were unable to query the database for PANEL_WEEK table information.", c.moveToFirst());

        Log.d(LOG_TAG, "checking columns for PANEL_WEEK table");
        final HashSet<String> panelWeekColumnHashSet = new HashSet<>();
        panelWeekColumnHashSet.add(PTContract.PanelWeek._ID);
        panelWeekColumnHashSet.add(PTContract.PanelWeek.COLUMN_COUNTRY_CODE);
        panelWeekColumnHashSet.add(PTContract.PanelWeek.COLUMN_PANEL_TYPE);
        panelWeekColumnHashSet.add(PTContract.PanelWeek.COLUMN_WEEK_CODE);
        panelWeekColumnHashSet.add(PTContract.PanelWeek.COLUMN_WEEK_DESC);
        panelWeekColumnHashSet.add(PTContract.PanelWeek.COLUMN_START_DATE);
        panelWeekColumnHashSet.add(PTContract.PanelWeek.COLUMN_END_DATE);
        panelWeekColumnHashSet.add(PTContract.PanelWeek.COLUMN_ACTIVE);
        panelWeekColumnHashSet.add(PTContract.PanelWeek.COLUMN_SYNC);
        panelWeekColumnHashSet.add(PTContract.PanelWeek.COLUMN_SYNC_DATE);

        columnNameIndex = c.getColumnIndex("name");
        do {
            panelWeekColumnHashSet.remove(c.getString(columnNameIndex));
        } while (c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required PANEL_WEEK columns", panelWeekColumnHashSet.isEmpty());

        //LOG
        c = db.rawQuery("PRAGMA table_info(" + PTContract.Log.TABLE_NAME + ")", null);
        assertTrue("Error: This means that we were unable to query the database for PANEL_WEEK table information.", c.moveToFirst());

        Log.d(LOG_TAG, "checking columns for LOG table");
        final HashSet<String> logColumnHashSet = new HashSet<>();
        logColumnHashSet.add(PTContract.Log._ID);
        logColumnHashSet.add(PTContract.Log.COLUMN_COUNTRY_CODE);
        logColumnHashSet.add(PTContract.Log.COLUMN_LOG_TYPE);
        logColumnHashSet.add(PTContract.Log.COLUMN_LOG_MESSAGE);
        logColumnHashSet.add(PTContract.Log.COLUMN_LOG_DATE);
        logColumnHashSet.add(PTContract.Log.COLUMN_VERSION);
        logColumnHashSet.add(PTContract.Log.COLUMN_USER);
        logColumnHashSet.add(PTContract.Log.COLUMN_ACTIVITY);
        logColumnHashSet.add(PTContract.Log.COLUMN_SYNC);
        logColumnHashSet.add(PTContract.Log.COLUMN_SYNC_DATE);

        columnNameIndex = c.getColumnIndex("name");
        do {
            logColumnHashSet.remove(c.getString(columnNameIndex));
        } while (c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required LOG columns", logColumnHashSet.isEmpty());


        c.close();
        db.close();
    }

    public void testFamInsertData() throws Throwable {
        SQLiteDatabase db = new PTDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Uri uri = PTContract.Fam.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(PTContract.Fam.COLUMN_COUNTRY_CODE, "TR");
        values.put(PTContract.Fam.COLUMN_FAM_CODE, "01W1048");
        values.put(PTContract.Fam.COLUMN_FAM_NAME, "TEST HANESI");
        values.put(PTContract.Fam.COLUMN_REG_BEG, "10.05.2012");
        values.put(PTContract.Fam.COLUMN_DISTRICT, "ADALAR");
        values.put(PTContract.Fam.COLUMN_PROVINCE, "ISTANBUL");
        values.put(PTContract.Fam.COLUMN_NEIGHBORHOOD, "HEYBELIADA");
        values.put(PTContract.Fam.COLUMN_ADDRESS, "HEYBETLI CAD. HEYBECI SOK. NO:2 D:3");
        values.put(PTContract.Fam.COLUMN_LANDLINE, "02165871030");
        values.put(PTContract.Fam.COLUMN_WORKLINE, "");
        values.put(PTContract.Fam.COLUMN_CELLULAR, "05359348602");
        values.put(PTContract.Fam.COLUMN_FLD_CODE, "G5");
        values.put(PTContract.Fam.COLUMN_VISIT_DAY, "4");
        values.put(PTContract.Fam.COLUMN_AVP, "1");
        values.put(PTContract.Fam.COLUMN_ALK, "0");
        values.put(PTContract.Fam.COLUMN_SP, "1");
        values.put(PTContract.Fam.COLUMN_EK_ALK, "0");
        values.put(PTContract.Fam.COLUMN_BABY, "0");
        values.put(PTContract.Fam.COLUMN_POINT, "4724");
        values.put(PTContract.Fam.COLUMN_ISUSER, "1");
        values.put(PTContract.Fam.COLUMN_ACTIVE, "1");
        values.put(PTContract.Fam.COLUMN_SYNC, "0");
        values.put(PTContract.Fam.COLUMN_SYNC_DATE, "");

        Uri returnUri = getContext().getContentResolver().insert(uri, values);
        assertTrue("FAM00 insert failed!", returnUri == null);
    }

    public void testIndInsertData() throws Throwable {
        SQLiteDatabase db = new PTDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Uri uri = PTContract.Ind.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(PTContract.Ind.COLUMN_COUNTRY_CODE, "TR");
        values.put(PTContract.Ind.COLUMN_FAM_CODE, "01W1048");
        values.put(PTContract.Ind.COLUMN_IND_CODE, "1");
        values.put(PTContract.Ind.COLUMN_IND_NAME, "TEST BIREY");
        values.put(PTContract.Ind.COLUMN_DOB, "01.01.1990");
        values.put(PTContract.Ind.COLUMN_PHONE, "05359348602");
        values.put(PTContract.Ind.COLUMN_PHONE2, "");
        values.put(PTContract.Ind.COLUMN_EMAIL, "ALI@VELI.COM");
        values.put(PTContract.Ind.COLUMN_EMAIL2, "");
        values.put(PTContract.Ind.COLUMN_SP, "0");
        values.put(PTContract.Ind.COLUMN_ALK, "0");
        values.put(PTContract.Ind.COLUMN_ISUSER, "1");
        values.put(PTContract.Ind.COLUMN_ACTIVE, "1");
        values.put(PTContract.Ind.COLUMN_SYNC, "0");
        values.put(PTContract.Ind.COLUMN_SYNC_DATE, "");

        Uri returnUri = getContext().getContentResolver().insert(uri, values);
        assertTrue("IND00 insert failed!", returnUri == null);
    }

    public void testFldInsertData() throws Throwable {
        SQLiteDatabase db = new PTDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Uri uri = PTContract.Fld.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(PTContract.Fld.COLUMN_COUNTRY_CODE, "TR");
        values.put(PTContract.Fld.COLUMN_FLD_CODE, "G5");
        values.put(PTContract.Fld.COLUMN_FLD_NAME, "ALI VELI");
        values.put(PTContract.Fld.COLUMN_PROVINCE, "ISTANBUL");
        values.put(PTContract.Fld.COLUMN_REGION, "ANADOLU");
        values.put(PTContract.Fld.COLUMN_PHONE, "05359348602");
        values.put(PTContract.Fld.COLUMN_PHONE2, "");
        values.put(PTContract.Fld.COLUMN_EMAIL, "ALI@VELI.COM");
        values.put(PTContract.Fld.COLUMN_EMAIL2, "");
        values.put(PTContract.Fld.COLUMN_ISUSER, "1");
        values.put(PTContract.Fld.COLUMN_ACTIVE, "1");
        values.put(PTContract.Fld.COLUMN_SYNC, "0");
        values.put(PTContract.Fld.COLUMN_SYNC_DATE, "");

        Uri returnUri = getContext().getContentResolver().insert(uri, values);
        assertTrue("FLD00 insert failed!", returnUri == null);
    }

    public void testPanelInsertData() throws Throwable {
        SQLiteDatabase db = new PTDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Uri uri = PTContract.Panel.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(PTContract.Panel.COLUMN_COUNTRY_CODE, "TR");
        values.put(PTContract.Panel.COLUMN_FLD_CODE, "G5");
        values.put(PTContract.Panel.COLUMN_PANEL_TYPE, "AVP");
        values.put(PTContract.Panel.COLUMN_FAM_CODE, "01W1049");
        values.put(PTContract.Panel.COLUMN_IND_CODE, "0");
        values.put(PTContract.Panel.COLUMN_WEEK_CODE, "7162");
        values.put(PTContract.Panel.COLUMN_WEEK_CHECK, "0");
        values.put(PTContract.Panel.COLUMN_SYNC, "0");
        values.put(PTContract.Panel.COLUMN_SYNC_DATE, "");

        Uri returnUri = getContext().getContentResolver().insert(uri, values);
        assertTrue("PANEL insert failed!", returnUri == null);
    }

    public void testPanelWeekInsertData() throws Throwable {
        SQLiteDatabase db = new PTDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Uri uri = PTContract.PanelWeek.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(PTContract.PanelWeek.COLUMN_COUNTRY_CODE, "TR");
        values.put(PTContract.PanelWeek.COLUMN_PANEL_TYPE, "AVP");
        values.put(PTContract.PanelWeek.COLUMN_WEEK_CODE, "7162");
        values.put(PTContract.PanelWeek.COLUMN_WEEK_DESC, "15 - 21 HAFTASI");
        values.put(PTContract.PanelWeek.COLUMN_START_DATE, "15");
        values.put(PTContract.PanelWeek.COLUMN_END_DATE, "21");
        values.put(PTContract.PanelWeek.COLUMN_ACTIVE, "1");
        values.put(PTContract.PanelWeek.COLUMN_SYNC, "0");
        values.put(PTContract.PanelWeek.COLUMN_SYNC_DATE, "");

        Uri returnUri = getContext().getContentResolver().insert(uri, values);
        assertTrue("PANEL WEEK insert failed!", returnUri == null);
    }

    public void testLogInsertData() throws Throwable {
        SQLiteDatabase db = new PTDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Uri uri = PTContract.Log.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(PTContract.Log.COLUMN_COUNTRY_CODE, "TR");
        values.put(PTContract.Log.COLUMN_LOG_TYPE, "ERROR");
        values.put(PTContract.Log.COLUMN_LOG_MESSAGE, "TEST");
        values.put(PTContract.Log.COLUMN_LOG_DATE, "05.01.2017");
        values.put(PTContract.Log.COLUMN_VERSION, "1.0.0");
        values.put(PTContract.Log.COLUMN_USER, "ALI");
        values.put(PTContract.Log.COLUMN_ACTIVITY, "LOGIN");
        values.put(PTContract.Log.COLUMN_SYNC, "0");
        values.put(PTContract.Log.COLUMN_SYNC_DATE, "");

        Uri returnUri = getContext().getContentResolver().insert(uri, values);
        assertTrue("LOG insert failed!", returnUri == null);
    }
}
