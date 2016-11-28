package com.ipsos.savascilve.ipsospt.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by zencifil on 17/11/2016.
 */

public class PTProvider extends ContentProvider {

    private PTDbHelper _dbHelper;
    private static final UriMatcher _uriMatcher = buildUriMatcher();
    static final int FAMILY = 100;
    static final int FAMILY_BY_VISIT_DAY = 101;
    static final int INDIVIDUAL = 200;
    //static final int

    private static final SQLiteQueryBuilder _queryBuilder;
    static {
        _queryBuilder = new SQLiteQueryBuilder();
    }

    //private Cursor getFamilyListByVisitDay(Uri uri, )

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PTContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, PTContract.PATH_FAMILY, FAMILY);
        matcher.addURI(authority, PTContract.PATH_FAMILY + "/*", FAMILY_BY_VISIT_DAY);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
