package com.ipsos.cpm.ipsospt.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zencifil on 17/11/2016.
 */

public class PTDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = PTDbHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "pt.db";

    public PTDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAM_TABLE = "CREATE TABLE " + PTContract.Fam.TABLE_NAME + " (" +
                PTContract.Fam._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTContract.Fam.COLUMN_FAM_CODE + " TEXT UNIQUE NOT NULL, " +
                PTContract.Fam.COLUMN_FAM_NAME + " TEXT NOT NULL, " +
                PTContract.Fam.COLUMN_CITY + " TEXT NOT NULL, " +
                PTContract.Fam.COLUMN_TOWN + " TEXT NOT NULL, " +
                PTContract.Fam.COLUMN_DISTRICT + " TEXT NOT NULL, " +
                PTContract.Fam.COLUMN_STREET + " TEXT, " +
                PTContract.Fam.COLUMN_ROAD + " TEXT, " +
                PTContract.Fam.COLUMN_HOUSE_NO + " TEXT, " +
                PTContract.Fam.COLUMN_DOOR_NO + " TEXT, " +
                PTContract.Fam.COLUMN_PHONE + " TEXT, " +
                PTContract.Fam.COLUMN_PHONE2 + " TEXT, " +
                PTContract.Fam.COLUMN_FLD_CODE + " TEXT, " +
                PTContract.Fam.COLUMN_ACTIVE + " INTEGER, " +
                PTContract.Fam.COLUMN_AVP + " INTEGER, " + //alisveris
                PTContract.Fam.COLUMN_ALK + " INTEGER, " + //alkol
                PTContract.Fam.COLUMN_SP + " INTEGER, " + //sigara
                PTContract.Fam.COLUMN_EK_ALK + " INTEGER, " + //ek alkol
                PTContract.Fam.COLUMN_BABY + " INTEGER, " + //bebek
                PTContract.Fam.COLUMN_HP + " INTEGER, " + //harcama
                PTContract.Fam.COLUMN_VISIT_DAY + " INTEGER, " +
                PTContract.Fam.COLUMN_POINT + " INTEGER);";

        final String SQL_CREATE_FLD_TABLE = "CREATE TABLE " + PTContract.Fld.TABLE_NAME + " (" +
                PTContract.Fld._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTContract.Fld.COLUMN_FLD_CODE + " TEXT UNIQUE NOT NULL, " +
                PTContract.Fld.COLUMN_FLD_NAME + " TEXT NOT NULL, " +
                PTContract.Fld.COLUMN_CITY + " TEXT, " +
                PTContract.Fld.COLUMN_PHONE + " TEXT, " +
                PTContract.Fld.COLUMN_PHONE2 + " TEXT, " +
                PTContract.Fld.COLUMN_EMAIL + " TEXT, " +
                PTContract.Fld.COLUMN_EMAIL2 + " TEXT);";

        final String SQL_CREATE_IND_TABLE = "CREATE TABLE " + PTContract.Ind.TABLE_NAME + " (" +
                PTContract.Ind._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTContract.Ind.COLUMN_FAM_CODE + " TEXT NOT NULL, " +
                PTContract.Ind.COLUMN_IND_CODE + " INTEGER NOT NULL, " +
                PTContract.Ind.COLUMN_IND_NAME + " TEXT NOT NULL, " +
                PTContract.Ind.COLUMN_PHONE + " TEXT, " +
                PTContract.Ind.COLUMN_PHONE2 + " TEXT, " +
                PTContract.Ind.COLUMN_EMAIL + " TEXT, " +
                PTContract.Ind.COLUMN_EMAIL2 + " TEXT, " +
                PTContract.Ind.COLUMN_SP + " TEXT, " +
                PTContract.Ind.COLUMN_ALK + " TEXT, " +
                PTContract.Ind.COLUMN_FP + " TEXT);";

        final String SQL_CREATE_PANELFAM_TABLE = "CREATE TABLE " + PTContract.PanelFam.TABLE_NAME + " (" +
                PTContract.PanelFam._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTContract.PanelFam.COLUMN_FLD_CODE + " TEXT NOT NULL, " +
                PTContract.PanelFam.COLUMN_PANEL_TYPE + " TEXT NOT NULL, " +
                PTContract.PanelFam.COLUMN_FAM_CODE + " TEXT NOT NULL, " +
                PTContract.PanelFam.COLUMN_WEEK1 + " INTEGER NOT NULL, " +
                PTContract.PanelFam.COLUMN_WEEK2 + " INTEGER NOT NULL, " +
                PTContract.PanelFam.COLUMN_WEEK3 + " INTEGER NOT NULL, " +
                PTContract.PanelFam.COLUMN_WEEK4 + " INTEGER NOT NULL, " +
                PTContract.PanelFam.COLUMN_WEEK5 + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_FAM_TABLE);
        Log.d(LOG_TAG, "Family table created");
        sqLiteDatabase.execSQL(SQL_CREATE_IND_TABLE);
        Log.d(LOG_TAG, "Individual table created");
        sqLiteDatabase.execSQL(SQL_CREATE_FLD_TABLE);
        Log.d(LOG_TAG, "PT table created");
        sqLiteDatabase.execSQL(SQL_CREATE_PANELFAM_TABLE);
        Log.d(LOG_TAG, "Panel Fam table created");

        //TODO dont forget to close!!!
        //for test purpose
        String testFamInsert = "INSERT INTO " + PTContract.Fam.TABLE_NAME +
                " (" + PTContract.Fam.COLUMN_FAM_CODE + ", " + PTContract.Fam.COLUMN_FAM_NAME +
                ", " + PTContract.Fam.COLUMN_CITY + ", " + PTContract.Fam.COLUMN_TOWN +
                ", " + PTContract.Fam.COLUMN_DISTRICT + ", " + PTContract.Fam.COLUMN_STREET +
                ", " + PTContract.Fam.COLUMN_ROAD + ", " + PTContract.Fam.COLUMN_HOUSE_NO +
                ", " + PTContract.Fam.COLUMN_DOOR_NO + ", " + PTContract.Fam.COLUMN_PHONE +
                ", " + PTContract.Fam.COLUMN_PHONE2 + ", " + PTContract.Fam.COLUMN_FLD_CODE +
                ", " + PTContract.Fam.COLUMN_ACTIVE + ", " + PTContract.Fam.COLUMN_AVP +
                ", " + PTContract.Fam.COLUMN_ALK + ", " + PTContract.Fam.COLUMN_SP +
                ", " + PTContract.Fam.COLUMN_EK_ALK + ", " + PTContract.Fam.COLUMN_BABY +
                ", " + PTContract.Fam.COLUMN_HP + ", " + PTContract.Fam.COLUMN_VISIT_DAY +
                ", " + PTContract.Fam.COLUMN_POINT + ") " +
                " VALUES ('01W1049', 'TEST HANESI', 'ISTANBUL', 'KADIKOY', '19 MAYIS MAH.', 'INONU', NULL, '50', NULL, " +
                " '5647813', '7528394152', 'X0', 1, 1, 1, 1, 0, 0, 0, 4, 570);";
        sqLiteDatabase.execSQL(testFamInsert);
        testFamInsert = "INSERT INTO " + PTContract.Fam.TABLE_NAME +
                " (" + PTContract.Fam.COLUMN_FAM_CODE + ", " + PTContract.Fam.COLUMN_FAM_NAME +
                ", " + PTContract.Fam.COLUMN_CITY + ", " + PTContract.Fam.COLUMN_TOWN +
                ", " + PTContract.Fam.COLUMN_DISTRICT + ", " + PTContract.Fam.COLUMN_STREET +
                ", " + PTContract.Fam.COLUMN_ROAD + ", " + PTContract.Fam.COLUMN_HOUSE_NO +
                ", " + PTContract.Fam.COLUMN_DOOR_NO + ", " + PTContract.Fam.COLUMN_PHONE +
                ", " + PTContract.Fam.COLUMN_PHONE2 + ", " + PTContract.Fam.COLUMN_FLD_CODE +
                ", " + PTContract.Fam.COLUMN_ACTIVE + ", " + PTContract.Fam.COLUMN_AVP +
                ", " + PTContract.Fam.COLUMN_ALK + ", " + PTContract.Fam.COLUMN_SP +
                ", " + PTContract.Fam.COLUMN_EK_ALK + ", " + PTContract.Fam.COLUMN_BABY +
                ", " + PTContract.Fam.COLUMN_HP + ", " + PTContract.Fam.COLUMN_VISIT_DAY +
                ", " + PTContract.Fam.COLUMN_POINT + ") " +
                " VALUES ('01W1050', 'TEST HANESI 2', 'ISTANBUL', 'ADALAR', 'HEYBELIADA MAH.', 'HEYBETLI', 'HEYBECI', '3', 2, " +
                " '5359348602', '5555517654', 'X0', 1, 1, 1, 0, 1, 0, 0, 4, 1270);";
        sqLiteDatabase.execSQL(testFamInsert);
        testFamInsert = "INSERT INTO " + PTContract.Fam.TABLE_NAME +
                " (" + PTContract.Fam.COLUMN_FAM_CODE + ", " + PTContract.Fam.COLUMN_FAM_NAME +
                ", " + PTContract.Fam.COLUMN_CITY + ", " + PTContract.Fam.COLUMN_TOWN +
                ", " + PTContract.Fam.COLUMN_DISTRICT + ", " + PTContract.Fam.COLUMN_STREET +
                ", " + PTContract.Fam.COLUMN_ROAD + ", " + PTContract.Fam.COLUMN_HOUSE_NO +
                ", " + PTContract.Fam.COLUMN_DOOR_NO + ", " + PTContract.Fam.COLUMN_PHONE +
                ", " + PTContract.Fam.COLUMN_PHONE2 + ", " + PTContract.Fam.COLUMN_FLD_CODE +
                ", " + PTContract.Fam.COLUMN_ACTIVE + ", " + PTContract.Fam.COLUMN_AVP +
                ", " + PTContract.Fam.COLUMN_ALK + ", " + PTContract.Fam.COLUMN_SP +
                ", " + PTContract.Fam.COLUMN_EK_ALK + ", " + PTContract.Fam.COLUMN_BABY +
                ", " + PTContract.Fam.COLUMN_HP + ", " + PTContract.Fam.COLUMN_VISIT_DAY +
                ", " + PTContract.Fam.COLUMN_POINT + ") " +
                " VALUES ('01W1051', 'TEST HANESI 3', 'ISTANBUL', 'ADALAR', 'HEYBELIADA MAH.', 'HEYBETLI', 'HEYBECI', '3', 2, " +
                " '5359348602', '5555517654', 'X0', 1, 1, 1, 0, 1, 0, 0, 3, 5265);";
        sqLiteDatabase.execSQL(testFamInsert);

        String testIndInsert = "INSERT INTO " + PTContract.Ind.TABLE_NAME + " (" +
                PTContract.Ind.COLUMN_FAM_CODE + ", " + PTContract.Ind.COLUMN_IND_CODE + ", " +
                PTContract.Ind.COLUMN_IND_NAME + ", " + PTContract.Ind.COLUMN_PHONE + ", " +
                PTContract.Ind.COLUMN_PHONE2 + ", " + PTContract.Ind.COLUMN_EMAIL + ", " +
                PTContract.Ind.COLUMN_EMAIL2 + ", " + PTContract.Ind.COLUMN_SP + ", " +
                PTContract.Ind.COLUMN_ALK + ", " + PTContract.Ind.COLUMN_FP + ") " +
                "VALUES ('01W1050', 1, 'TEST BIREY', '5555517654', NULL, 'SAVAS.CILVE@IPSOS.COM', NULL, 1, 1, 1);";
        sqLiteDatabase.execSQL(testIndInsert);
        testIndInsert = "INSERT INTO " + PTContract.Ind.TABLE_NAME + " (" +
                PTContract.Ind.COLUMN_FAM_CODE + ", " + PTContract.Ind.COLUMN_IND_CODE + ", " +
                PTContract.Ind.COLUMN_IND_NAME + ", " + PTContract.Ind.COLUMN_PHONE + ", " +
                PTContract.Ind.COLUMN_PHONE2 + ", " + PTContract.Ind.COLUMN_EMAIL + ", " +
                PTContract.Ind.COLUMN_EMAIL2 + ", " + PTContract.Ind.COLUMN_SP + ", " +
                PTContract.Ind.COLUMN_ALK + ", " + PTContract.Ind.COLUMN_FP + ") " +
                "VALUES ('01W1050', 2, 'TEST IKIEY', '5555517654', NULL, NULL, NULL, 1, 0, 0);";
        sqLiteDatabase.execSQL(testIndInsert);

        String testPanelFamInsert = "INSERT INTO " + PTContract.PanelFam.TABLE_NAME + " (" +
                PTContract.PanelFam.COLUMN_FLD_CODE + ", " + PTContract.PanelFam.COLUMN_PANEL_TYPE + ", " +
                PTContract.PanelFam.COLUMN_FAM_CODE + ", " + PTContract.PanelFam.COLUMN_WEEK1 + ", " +
                PTContract.PanelFam.COLUMN_WEEK2 + ", " + PTContract.PanelFam.COLUMN_WEEK3 + ", " +
                PTContract.PanelFam.COLUMN_WEEK4 + ", " + PTContract.PanelFam.COLUMN_WEEK5 + ") " +
                "VALUES ('G5', 'AVP', '01W1050', 2, 2, 1, 0, 0);";
        sqLiteDatabase.execSQL(testPanelFamInsert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Fam.TABLE_NAME);
        Log.d(LOG_TAG, "Family table dropped");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Fld.TABLE_NAME);
        Log.d(LOG_TAG, "Individual table dropped");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Ind.TABLE_NAME);
        Log.d(LOG_TAG, "PT table dropped");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.PanelFam.TABLE_NAME);
        Log.d(LOG_TAG, "Panel table dropped");
        onCreate(sqLiteDatabase);
    }
}
