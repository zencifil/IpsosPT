package com.ipsos.savascilve.ipsospt.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zencifil on 17/11/2016.
 */

public class PTDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "pt.db";

    public PTDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAM_TABLE = "CREATE TABLE " + PTContract.Fam.TABLE_NAME + " (" +
                PTContract.Fam._ID + " INTEGER PRIMARY KEY, " +
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
                PTContract.Fam.COLUMN_VISIT_DAY + " INTEGER);";

        final String SQL_CREATE_FLD_TABLE = "CREATE TABLE " + PTContract.Fld.TABLE_NAME + " (" +
                PTContract.Fld._ID + " INTEGER PRIMARY KEY, " +
                PTContract.Fld.COLUMN_FLD_CODE + " TEXT UNIQUE NOT NULL, " +
                PTContract.Fld.COLUMN_FLD_NAME + " TEXT NOT NULL, " +
                PTContract.Fld.COLUMN_CITY + " TEXT, " +
                PTContract.Fld.COLUMN_PHONE + " TEXT, " +
                PTContract.Fld.COLUMN_PHONE2 + " TEXT, " +
                PTContract.Fld.COLUMN_EMAIL + " TEXT, " +
                PTContract.Fld.COLUMN_EMAIL2 + " TEXT);";

        final String SQL_CREATE_IND_TABLE = "CREATE TABLE " + PTContract.Ind.TABLE_NAME + " (" +
                PTContract.Ind._ID + " INTEGER PRIMARY KEY, " +
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

        sqLiteDatabase.execSQL(SQL_CREATE_FAM_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_IND_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FLD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Fam.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Fld.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Ind.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
