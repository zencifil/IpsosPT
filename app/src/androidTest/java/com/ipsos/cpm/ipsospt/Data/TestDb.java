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

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables", tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + PTContract.Fam.TABLE_NAME + ")", null);

        assertTrue("Error: This means that we were unable to query the database for table information.", c.moveToFirst());

        Log.d(LOG_TAG, "checking columns");
        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> famColumnHashSet = new HashSet<>();
        famColumnHashSet.add(PTContract.Fam._ID);
        famColumnHashSet.add(PTContract.Fam.COLUMN_FAM_CODE);
        famColumnHashSet.add(PTContract.Fam.COLUMN_FAM_NAME);
        famColumnHashSet.add(PTContract.Fam.COLUMN_CITY);
        famColumnHashSet.add(PTContract.Fam.COLUMN_TOWN);
        famColumnHashSet.add(PTContract.Fam.COLUMN_DISTRICT);
        famColumnHashSet.add(PTContract.Fam.COLUMN_STREET);
        famColumnHashSet.add(PTContract.Fam.COLUMN_ROAD);
        famColumnHashSet.add(PTContract.Fam.COLUMN_HOUSE_NO);
        famColumnHashSet.add(PTContract.Fam.COLUMN_DOOR_NO);
        famColumnHashSet.add(PTContract.Fam.COLUMN_PHONE);
        famColumnHashSet.add(PTContract.Fam.COLUMN_PHONE2);
        famColumnHashSet.add(PTContract.Fam.COLUMN_ACTIVE);
        famColumnHashSet.add(PTContract.Fam.COLUMN_AVP);
        famColumnHashSet.add(PTContract.Fam.COLUMN_ALK);
        famColumnHashSet.add(PTContract.Fam.COLUMN_EK_ALK);
        famColumnHashSet.add(PTContract.Fam.COLUMN_BABY);
        famColumnHashSet.add(PTContract.Fam.COLUMN_SP);
        famColumnHashSet.add(PTContract.Fam.COLUMN_FLD_CODE);
        famColumnHashSet.add(PTContract.Fam.COLUMN_VISIT_DAY);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            famColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns", famColumnHashSet.isEmpty());

        c.close();
        db.close();
    }

}
