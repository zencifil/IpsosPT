package com.ipsos.savascilve.ipsospt.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zencifil on 17/11/2016.
 */

public class PTDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "pt.db";

    public PTDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //TODO create database
        final String CREATE_FAMILY_TABLE = "";
        final String CREATE_FLD_TABLE = "";
        final String CREATE_IND_TABLE = "";

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Family.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Fld.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PTContract.Ind.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
