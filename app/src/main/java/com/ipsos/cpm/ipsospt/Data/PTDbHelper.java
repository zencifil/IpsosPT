package com.ipsos.cpm.ipsospt.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class PTDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = PTDbHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "pt.db";

    PTDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAM_TABLE = "CREATE TABLE " + PTContract.Fam.TABLE_NAME + " (" +
                PTContract.Fam._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTContract.Fam.COLUMN_COUNTRY_CODE + " TEXT NOT NULL, " +
                PTContract.Fam.COLUMN_FAM_CODE + " TEXT UNIQUE NOT NULL, " +
                PTContract.Fam.COLUMN_FAM_NAME + " TEXT NOT NULL, " +
                PTContract.Fam.COLUMN_REG_BEG + " TEXT NOT NULL, " +
                PTContract.Fam.COLUMN_DISTRICT + " TEXT NOT NULL, " +
                PTContract.Fam.COLUMN_PROVINCE + " TEXT, " +
                PTContract.Fam.COLUMN_NEIGHBORHOOD + " TEXT, " +
                PTContract.Fam.COLUMN_ADDRESS + " TEXT, " +
                PTContract.Fam.COLUMN_LANDLINE + " TEXT, " +
                PTContract.Fam.COLUMN_WORKLINE + " TEXT, " +
                PTContract.Fam.COLUMN_CELLULAR + " TEXT, " +
                PTContract.Fam.COLUMN_FLD_CODE + " TEXT, " +
                PTContract.Fam.COLUMN_VISIT_DAY + " INTEGER, " +
                PTContract.Fam.COLUMN_AVP + " INTEGER, " + //alisveris
                PTContract.Fam.COLUMN_ALK + " INTEGER, " + //alkol
                PTContract.Fam.COLUMN_SP + " INTEGER, " + //sigara
                PTContract.Fam.COLUMN_EK_ALK + " INTEGER, " + //ek alkol
                PTContract.Fam.COLUMN_BABY + " INTEGER, " + //bebek
                PTContract.Fam.COLUMN_POINT + " INTEGER, " +
                PTContract.Fam.COLUMN_ISUSER + " INTEGER, " +
                PTContract.Fam.COLUMN_ACTIVE + " INTEGER, " +
                PTContract.Fam.COLUMN_SYNC + " INTEGER, " +
                PTContract.Fam.COLUMN_SYNC_DATE + " TEXT " +
                ");";

        final String SQL_CREATE_FLD_TABLE = "CREATE TABLE " + PTContract.Fld.TABLE_NAME + " (" +
                PTContract.Fld._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTContract.Fld.COLUMN_COUNTRY_CODE + " TEXT NOT NULL, " +
                PTContract.Fld.COLUMN_FLD_CODE + " TEXT UNIQUE NOT NULL, " +
                PTContract.Fld.COLUMN_FLD_NAME + " TEXT NOT NULL, " +
                PTContract.Fld.COLUMN_PROVINCE + " TEXT, " +
                PTContract.Fld.COLUMN_REGION + " TEXT, " +
                PTContract.Fld.COLUMN_PHONE + " TEXT, " +
                PTContract.Fld.COLUMN_PHONE2 + " TEXT, " +
                PTContract.Fld.COLUMN_EMAIL + " TEXT, " +
                PTContract.Fld.COLUMN_EMAIL2 + " TEXT, " +
                PTContract.Fld.COLUMN_ISUSER + " INTEGER, " +
                PTContract.Fld.COLUMN_ACTIVE + " INTEGER, " +
                PTContract.Fld.COLUMN_SYNC + " INTEGER, " +
                PTContract.Fld.COLUMN_SYNC_DATE + " TEXT " +
                ");";

        final String SQL_CREATE_IND_TABLE = "CREATE TABLE " + PTContract.Ind.TABLE_NAME + " (" +
                PTContract.Ind._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTContract.Ind.COLUMN_COUNTRY_CODE + " TEXT NOT NULL, " +
                PTContract.Ind.COLUMN_FAM_CODE + " TEXT NOT NULL, " +
                PTContract.Ind.COLUMN_IND_CODE + " INTEGER NOT NULL, " +
                PTContract.Ind.COLUMN_IND_NAME + " TEXT NOT NULL, " +
                PTContract.Ind.COLUMN_DOB + " TEXT, " +
                PTContract.Ind.COLUMN_PHONE + " TEXT, " +
                PTContract.Ind.COLUMN_PHONE2 + " TEXT, " +
                PTContract.Ind.COLUMN_EMAIL + " TEXT, " +
                PTContract.Ind.COLUMN_EMAIL2 + " TEXT, " +
                PTContract.Ind.COLUMN_SP + " TEXT, " +
                PTContract.Ind.COLUMN_ALK + " TEXT, " +
                PTContract.Ind.COLUMN_ISUSER + " INTEGER, " +
                PTContract.Ind.COLUMN_ACTIVE + " INTEGER, " +
                PTContract.Ind.COLUMN_SYNC + " INTEGER, " +
                PTContract.Ind.COLUMN_SYNC_DATE + " TEXT " +
                ");";

        final String SQL_CREATE_PANEL_TABLE = "CREATE TABLE " + PTContract.Panel.TABLE_NAME + " (" +
                PTContract.Panel._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTContract.Panel.COLUMN_COUNTRY_CODE + " TEXT NOT NULL, " +
                PTContract.Panel.COLUMN_FLD_CODE + " TEXT NOT NULL, " +
                PTContract.Panel.COLUMN_PANEL_TYPE + " TEXT NOT NULL, " +
                PTContract.Panel.COLUMN_FAM_CODE + " TEXT NOT NULL, " +
                PTContract.Panel.COLUMN_IND_CODE + " INTEGER NOT NULL, " +
                PTContract.Panel.COLUMN_WEEK_CODE + " INTEGER NOT NULL, " +
                PTContract.Panel.COLUMN_WEEK_CHECK + " INTEGER NOT NULL, " +
                PTContract.Panel.COLUMN_SYNC + " INTEGER, " +
                PTContract.Panel.COLUMN_SYNC_DATE + " TEXT " +
                ");";

        final String SQL_CREATE_PANEL_WEEK_TABLE = "CREATE TABLE " + PTContract.PanelWeek.TABLE_NAME + " (" +
                PTContract.PanelWeek._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTContract.PanelWeek.COLUMN_COUNTRY_CODE + " TEXT NOT NULL, " +
                PTContract.PanelWeek.COLUMN_PANEL_TYPE + " TEXT NOT NULL, " +
                PTContract.PanelWeek.COLUMN_WEEK_CODE + " INTEGER NOT NULL, " +
                PTContract.PanelWeek.COLUMN_WEEK_DESC + " TEXT NOT NULL, " +
                PTContract.PanelWeek.COLUMN_START_DATE + " TEXT NOT NULL, " +
                PTContract.PanelWeek.COLUMN_END_DATE + " TEXT NOT NULL, " +
                PTContract.PanelWeek.COLUMN_ACTIVE + " INTEGER NOT NULL, " +
                PTContract.PanelWeek.COLUMN_SYNC + " INTEGER, " +
                PTContract.PanelWeek.COLUMN_SYNC_DATE + " TEXT " +
                ");";

        final String SQL_CREATE_USER_INFO_TABLE = "CREATE TABLE " + PTContract.UserInfo.TABLE_NAME + " (" +
                PTContract.UserInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTContract.UserInfo.COLUMN_AUTH_KEY + " TEXT NOT NULL, " +
                PTContract.UserInfo.COLUMN_AK_OBTAIN_DATE + " TEXT NOT NULL, " +
                PTContract.UserInfo.COLUMN_AK_VALID_UNTIL + " TEXT NOT NULL, " +
                PTContract.UserInfo.COLUMN_LAST_SYNC_DATE + " TEXT " +
                ");";

        final String SQL_CREATE_LOG_TABLE = "CREATE TABLE " + PTContract.Log.TABLE_NAME + " (" +
                PTContract.Log._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTContract.Log.COLUMN_COUNTRY_CODE + " TEXT NOT NULL, " +
                PTContract.Log.COLUMN_LOG_TYPE + " TEXT NOT NULL, " +
                PTContract.Log.COLUMN_LOG_MESSAGE + " TEXT NOT NULL, " +
                PTContract.Log.COLUMN_LOG_DATE + " TEXT NOT NULL, " +
                PTContract.Log.COLUMN_VERSION + " TEXT, " +
                PTContract.Log.COLUMN_USER + " TEXT, " +
                PTContract.Log.COLUMN_ACTIVITY + " TEXT, " +
                PTContract.Log.COLUMN_SYNC + " INTEGER, " +
                PTContract.Log.COLUMN_SYNC_DATE + " TEXT " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_FAM_TABLE);
        Log.d(LOG_TAG, "Family table created");
        sqLiteDatabase.execSQL(SQL_CREATE_IND_TABLE);
        Log.d(LOG_TAG, "Individual table created");
        sqLiteDatabase.execSQL(SQL_CREATE_FLD_TABLE);
        Log.d(LOG_TAG, "PT table created");
        sqLiteDatabase.execSQL(SQL_CREATE_PANEL_TABLE);
        Log.d(LOG_TAG, "Panel table created");
        sqLiteDatabase.execSQL(SQL_CREATE_PANEL_WEEK_TABLE);
        Log.d(LOG_TAG, "Panel Week table created");
        sqLiteDatabase.execSQL(SQL_CREATE_USER_INFO_TABLE);
        Log.d(LOG_TAG, "User Info table created");
        sqLiteDatabase.execSQL(SQL_CREATE_LOG_TABLE);
        Log.d(LOG_TAG, "Log table created");

        String query = "INSERT INTO " + PTContract.Fam.TABLE_NAME + " (" +
                PTContract.Fam.COLUMN_COUNTRY_CODE + ", " + PTContract.Fam.COLUMN_FAM_CODE + ", " +
                PTContract.Fam.COLUMN_FAM_NAME + ", " + PTContract.Fam.COLUMN_REG_BEG + ", " +
                PTContract.Fam.COLUMN_DISTRICT + ", " + PTContract.Fam.COLUMN_PROVINCE + ", " +
                PTContract.Fam.COLUMN_NEIGHBORHOOD + ", " + PTContract.Fam.COLUMN_ADDRESS + ", " +
                PTContract.Fam.COLUMN_LANDLINE + ", " + PTContract.Fam.COLUMN_WORKLINE + ", " +
                PTContract.Fam.COLUMN_CELLULAR + ", " + PTContract.Fam.COLUMN_FLD_CODE + ", " +
                PTContract.Fam.COLUMN_VISIT_DAY + ", " + PTContract.Fam.COLUMN_AVP + ", " +
                PTContract.Fam.COLUMN_ALK + ", " + PTContract.Fam.COLUMN_SP + ", " +
                PTContract.Fam.COLUMN_EK_ALK + ", " + PTContract.Fam.COLUMN_BABY + ", " +
                PTContract.Fam.COLUMN_POINT + ") " +
                "VALUES ('TR', '01W1049', 'TEST HANESI', '25.06.2012', 'ADALAR', 'ISTANBUL', " +
                "'HEYBELIADA MAH.', 'HEYBETLI CAD. HEYBECI SOK. NO:2 D:3', '02165871030', NULL, " +
                "'5359348602', 'G5', 3, 1, 1, 0, 0, 0, 2750);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT INTO " + PTContract.Fam.TABLE_NAME + " (" +
                PTContract.Fam.COLUMN_COUNTRY_CODE + ", " + PTContract.Fam.COLUMN_FAM_CODE + ", " +
                PTContract.Fam.COLUMN_FAM_NAME + ", " + PTContract.Fam.COLUMN_REG_BEG + ", " +
                PTContract.Fam.COLUMN_DISTRICT + ", " + PTContract.Fam.COLUMN_PROVINCE + ", " +
                PTContract.Fam.COLUMN_NEIGHBORHOOD + ", " + PTContract.Fam.COLUMN_ADDRESS + ", " +
                PTContract.Fam.COLUMN_LANDLINE + ", " + PTContract.Fam.COLUMN_WORKLINE + ", " +
                PTContract.Fam.COLUMN_CELLULAR + ", " + PTContract.Fam.COLUMN_FLD_CODE + ", " +
                PTContract.Fam.COLUMN_VISIT_DAY + ", " + PTContract.Fam.COLUMN_AVP + ", " +
                PTContract.Fam.COLUMN_ALK + ", " + PTContract.Fam.COLUMN_SP + ", " +
                PTContract.Fam.COLUMN_EK_ALK + ", " + PTContract.Fam.COLUMN_BABY + ", " +
                PTContract.Fam.COLUMN_POINT + ") " +
                "VALUES ('TR', '01W1050', 'TEST HANESI 2', '01.06.2013', 'KADIKOY', 'ISTANBUL', " +
                "'BOSTANCI MAH.', 'BOSTAN CAD. BOSTANLI SOK. NO:2 D:3', '02165871030', NULL, " +
                "'5359348602', 'G5', 3, 1, 1, 0, 0, 0, 2750);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT INTO " + PTContract.Ind.TABLE_NAME + " (" +
                PTContract.Ind.COLUMN_COUNTRY_CODE + ", " + PTContract.Ind.COLUMN_FAM_CODE + ", " +
                PTContract.Ind.COLUMN_IND_CODE + ", " + PTContract.Ind.COLUMN_IND_NAME + ", " +
                PTContract.Ind.COLUMN_DOB + ", " + PTContract.Ind.COLUMN_PHONE + ", " +
                PTContract.Ind.COLUMN_PHONE2 + ", " + PTContract.Ind.COLUMN_EMAIL + ", " +
                PTContract.Ind.COLUMN_EMAIL2 + ", " + PTContract.Ind.COLUMN_SP + ", " +
                PTContract.Ind.COLUMN_ALK + ") " +
                "VALUES ('TR', '01W1049', 1, 'TEST 1', '29.02.1980', '05359348602', NULL, 'ALI@VELI.COM', NULL, 0, 1);";
        sqLiteDatabase.execSQL(query);

        query = "INSERT INTO " + PTContract.Panel.TABLE_NAME + " (" +
                PTContract.Panel.COLUMN_COUNTRY_CODE + ", " + PTContract.Panel.COLUMN_PANEL_TYPE + ", " +
                PTContract.Panel.COLUMN_FLD_CODE + ", " + PTContract.Panel.COLUMN_FAM_CODE + ", " +
                PTContract.Panel.COLUMN_IND_CODE + ", " + PTContract.Panel.COLUMN_WEEK_CODE + ", " +
                PTContract.Panel.COLUMN_WEEK_CHECK + ", " + PTContract.Panel.COLUMN_SYNC + ") " +
                "VALUES ('TR', 'AVP', 'G5', '01W1049', 0, 7155, 0, 0);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT INTO " + PTContract.Panel.TABLE_NAME + " (" +
                PTContract.Panel.COLUMN_COUNTRY_CODE + ", " + PTContract.Panel.COLUMN_PANEL_TYPE + ", " +
                PTContract.Panel.COLUMN_FLD_CODE + ", " + PTContract.Panel.COLUMN_FAM_CODE + ", " +
                PTContract.Panel.COLUMN_IND_CODE + ", " + PTContract.Panel.COLUMN_WEEK_CODE + ", " +
                PTContract.Panel.COLUMN_WEEK_CHECK + ", " + PTContract.Panel.COLUMN_SYNC + ") " +
                "VALUES ('TR', 'SP', 'G5', '01W1049', 1, 7162, 0, 0);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT INTO " + PTContract.Panel.TABLE_NAME + " (" +
                PTContract.Panel.COLUMN_COUNTRY_CODE + ", " + PTContract.Panel.COLUMN_PANEL_TYPE + ", " +
                PTContract.Panel.COLUMN_FLD_CODE + ", " + PTContract.Panel.COLUMN_FAM_CODE + ", " +
                PTContract.Panel.COLUMN_IND_CODE + ", " + PTContract.Panel.COLUMN_WEEK_CODE + ", " +
                PTContract.Panel.COLUMN_WEEK_CHECK + ", " + PTContract.Panel.COLUMN_SYNC + ") " +
                "VALUES ('TR', 'SP', 'G5', '01W1049', 1, 7169, 0, 0);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT INTO " + PTContract.Panel.TABLE_NAME + " (" +
                PTContract.Panel.COLUMN_COUNTRY_CODE + ", " + PTContract.Panel.COLUMN_PANEL_TYPE + ", " +
                PTContract.Panel.COLUMN_FLD_CODE + ", " + PTContract.Panel.COLUMN_FAM_CODE + ", " +
                PTContract.Panel.COLUMN_IND_CODE + ", " + PTContract.Panel.COLUMN_WEEK_CODE + ", " +
                PTContract.Panel.COLUMN_WEEK_CHECK + ", " + PTContract.Panel.COLUMN_SYNC + ") " +
                "VALUES ('TR', 'AVP', 'G5', '01W1050', 0, 7155, 0, 0);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT INTO " + PTContract.Panel.TABLE_NAME + " (" +
                PTContract.Panel.COLUMN_COUNTRY_CODE + ", " + PTContract.Panel.COLUMN_PANEL_TYPE + ", " +
                PTContract.Panel.COLUMN_FLD_CODE + ", " + PTContract.Panel.COLUMN_FAM_CODE + ", " +
                PTContract.Panel.COLUMN_IND_CODE + ", " + PTContract.Panel.COLUMN_WEEK_CODE + ", " +
                PTContract.Panel.COLUMN_WEEK_CHECK + ", " + PTContract.Panel.COLUMN_SYNC + ") " +
                "VALUES ('TR', 'AVP', 'G5', '01W1050', 0, 7162, 0, 0);";
        sqLiteDatabase.execSQL(query);

        query = "INSERT INTO " + PTContract.PanelWeek.TABLE_NAME + " (" +
                PTContract.PanelWeek.COLUMN_COUNTRY_CODE + ", " + PTContract.PanelWeek.COLUMN_PANEL_TYPE + ", " +
                PTContract.PanelWeek.COLUMN_WEEK_CODE + ", " + PTContract.PanelWeek.COLUMN_WEEK_DESC + ", " +
                PTContract.PanelWeek.COLUMN_ACTIVE + ", " + PTContract.PanelWeek.COLUMN_START_DATE + ", " +
                PTContract.PanelWeek.COLUMN_END_DATE + ", " + PTContract.PanelWeek.COLUMN_SYNC + ") " +
                "VALUES ('TR', 'AVP', 7155, '8 - 14 HAFTASI', 1, '8', '14', 0);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT INTO " + PTContract.PanelWeek.TABLE_NAME + " (" +
                PTContract.PanelWeek.COLUMN_COUNTRY_CODE + ", " + PTContract.PanelWeek.COLUMN_PANEL_TYPE + ", " +
                PTContract.PanelWeek.COLUMN_WEEK_CODE + ", " + PTContract.PanelWeek.COLUMN_WEEK_DESC + ", " +
                PTContract.PanelWeek.COLUMN_ACTIVE + ", " + PTContract.PanelWeek.COLUMN_START_DATE + ", " +
                PTContract.PanelWeek.COLUMN_END_DATE + ", " + PTContract.PanelWeek.COLUMN_SYNC + ") " +
                "VALUES ('TR', 'AVP', 7162, '15 - 21 HAFTASI', 1, '15', '21', 0);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT INTO " + PTContract.PanelWeek.TABLE_NAME + " (" +
                PTContract.PanelWeek.COLUMN_COUNTRY_CODE + ", " + PTContract.PanelWeek.COLUMN_PANEL_TYPE + ", " +
                PTContract.PanelWeek.COLUMN_WEEK_CODE + ", " + PTContract.PanelWeek.COLUMN_WEEK_DESC + ", " +
                PTContract.PanelWeek.COLUMN_ACTIVE + ", " + PTContract.PanelWeek.COLUMN_START_DATE + ", " +
                PTContract.PanelWeek.COLUMN_END_DATE + ", " + PTContract.PanelWeek.COLUMN_SYNC + ") " +
                "VALUES ('TR', 'AVP', 7169, '22 - 28 HAFTASI', 1, '22', '28', 0);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT INTO " + PTContract.PanelWeek.TABLE_NAME + " (" +
                PTContract.PanelWeek.COLUMN_COUNTRY_CODE + ", " + PTContract.PanelWeek.COLUMN_PANEL_TYPE + ", " +
                PTContract.PanelWeek.COLUMN_WEEK_CODE + ", " + PTContract.PanelWeek.COLUMN_WEEK_DESC + ", " +
                PTContract.PanelWeek.COLUMN_ACTIVE + ", " + PTContract.PanelWeek.COLUMN_START_DATE + ", " +
                PTContract.PanelWeek.COLUMN_END_DATE + ", " + PTContract.PanelWeek.COLUMN_SYNC + ") " +
                "VALUES ('TR', 'SP', 7155, '8 - 14 HAFTASI', 1, '8', '14', 0);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT INTO " + PTContract.PanelWeek.TABLE_NAME + " (" +
                PTContract.PanelWeek.COLUMN_COUNTRY_CODE + ", " + PTContract.PanelWeek.COLUMN_PANEL_TYPE + ", " +
                PTContract.PanelWeek.COLUMN_WEEK_CODE + ", " + PTContract.PanelWeek.COLUMN_WEEK_DESC + ", " +
                PTContract.PanelWeek.COLUMN_ACTIVE + ", " + PTContract.PanelWeek.COLUMN_START_DATE + ", " +
                PTContract.PanelWeek.COLUMN_END_DATE + ", " + PTContract.PanelWeek.COLUMN_SYNC + ") " +
                "VALUES ('TR', 'SP', 7162, '15 - 21 HAFTASI', 1, '15', '21', 0);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT INTO " + PTContract.PanelWeek.TABLE_NAME + " (" +
                PTContract.PanelWeek.COLUMN_COUNTRY_CODE + ", " + PTContract.PanelWeek.COLUMN_PANEL_TYPE + ", " +
                PTContract.PanelWeek.COLUMN_WEEK_CODE + ", " + PTContract.PanelWeek.COLUMN_WEEK_DESC + ", " +
                PTContract.PanelWeek.COLUMN_ACTIVE + ", " + PTContract.PanelWeek.COLUMN_START_DATE + ", " +
                PTContract.PanelWeek.COLUMN_END_DATE + ", " + PTContract.PanelWeek.COLUMN_SYNC + ") " +
                "VALUES ('TR', 'SP', 7169, '22 - 28 HAFTASI', 1, '22', '28', 0);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Fam.TABLE_NAME);
        Log.d(LOG_TAG, "Family table dropped");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Fld.TABLE_NAME);
        Log.d(LOG_TAG, "Individual table dropped");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Ind.TABLE_NAME);
        Log.d(LOG_TAG, "PT table dropped");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Panel.TABLE_NAME);
        Log.d(LOG_TAG, "Panel table dropped");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.PanelWeek.TABLE_NAME);
        Log.d(LOG_TAG, "Panel week table dropped");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.UserInfo.TABLE_NAME);
        Log.d(LOG_TAG, "User Info table dropped");
        sqLiteDatabase.execSQL("DROP TABLE IS EXISTS " + PTContract.Log.TABLE_NAME);
        Log.d(LOG_TAG, "Log table dropped");
        onCreate(sqLiteDatabase);
    }
}
