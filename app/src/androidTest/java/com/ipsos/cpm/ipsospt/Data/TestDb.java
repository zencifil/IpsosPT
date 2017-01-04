package com.ipsos.cpm.ipsospt.Data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        Log.d(LOG_TAG, "checking columns for PANEL_WEEK table");
        final HashSet<String> logColumnHashSet = new HashSet<>();
        logColumnHashSet.add(PTContract.Log._ID);
        logColumnHashSet.add(PTContract.Log.COLUMN_COUNTRY_CODE);
        logColumnHashSet.add(PTContract.Log.COLUMN_LOG_TYPE);
        logColumnHashSet.add(PTContract.Log.COLUMN_LOG_MESSAGE);
        logColumnHashSet.add(PTContract.Log.COLUMN_LOG_DATE);
        logColumnHashSet.add(PTContract.Log.COLUMN_VERSION);
        logColumnHashSet.add(PTContract.Log.COLUMN_USER);
        logColumnHashSet.add(PTContract.Log.COLUMN_ACTIVITY);

        columnNameIndex = c.getColumnIndex("name");
        do {
            logColumnHashSet.remove(c.getString(columnNameIndex));
        } while (c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required LOG columns", logColumnHashSet.isEmpty());


        c.close();
        db.close();
    }


}
