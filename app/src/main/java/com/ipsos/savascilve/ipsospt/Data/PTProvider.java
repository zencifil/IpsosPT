package com.ipsos.savascilve.ipsospt.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by zencifil on 17/11/2016.
 */

public class PTProvider extends ContentProvider {

    private PTDbHelper _dbHelper;
    private static final UriMatcher _uriMatcher = buildUriMatcher();
    static final int FAMILY = 100;
    static final int FAMILY_BY_VISIT_DAY = 101;
    static final int FAMILY_BY_FAM_CODE = 102;
    static final int INDIVIDUAL = 200;
    static final int INDIVIDUAL_BY_FAMILY = 201;
    static final int INDIVIDUAL_BY_FAM_AND_IND_CODE = 202;

    private static final SQLiteQueryBuilder _famQueryBuilder;
    private static final SQLiteQueryBuilder _indQueryBuilder;
    private static final SQLiteQueryBuilder _fldQueryBuilder;
    static {
        _famQueryBuilder = new SQLiteQueryBuilder();
        _indQueryBuilder = new SQLiteQueryBuilder();
        _fldQueryBuilder = new SQLiteQueryBuilder();

        _famQueryBuilder.setTables(PTContract.Fam.TABLE_NAME);
        _indQueryBuilder.setTables(PTContract.Ind.TABLE_NAME);
        _fldQueryBuilder.setTables(PTContract.Fld.TABLE_NAME);
    }

    private static final String _familyByVisitDaySelection = PTContract.Fam.TABLE_NAME + "." + PTContract.Fam.COLUMN_VISIT_DAY + " = ? ";
    private static final String _familyByFamCodeSelection = PTContract.Fam.TABLE_NAME + "." + PTContract.Fam.COLUMN_FAM_CODE + " = ? ";
    private static final String _individualsByFamCodeSelection = PTContract.Ind.TABLE_NAME + "." + PTContract.Ind.COLUMN_FAM_CODE + " = ? ";
    private static final String _individualByFamAndIndCodeSelection = PTContract.Ind.TABLE_NAME + "." + PTContract.Ind.COLUMN_FAM_CODE + " = ? AND " +
            PTContract.Ind.TABLE_NAME + "." + PTContract.Ind.COLUMN_IND_CODE + " = ? ";

    private Cursor getFamilyListByVisitDay(String[] projection, String[] selectionArgs, String sortOrder) {
        String selection = _familyByVisitDaySelection;
        return _famQueryBuilder.query(_dbHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    private Cursor getFamilyByFamCode(String[] projection, String[] selectionArgs, String sortOrder) {
        String selection = _familyByFamCodeSelection;
        return _famQueryBuilder.query(_dbHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    private Cursor getIndividualsByFamCode(String[] projection, String[] selectionArgs, String sortOrder) {
        String selection = _individualsByFamCodeSelection;
        return _indQueryBuilder.query(_dbHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    private Cursor getIndividualByFamAndIndCode(String[] projection, String[] selectionArgs, String sortOrder) {
        String selection = _individualByFamAndIndCodeSelection;
        return _indQueryBuilder.query(_dbHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PTContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, PTContract.PATH_FAMILY, FAMILY);
        matcher.addURI(authority, PTContract.PATH_FAMILY + "/#", FAMILY_BY_VISIT_DAY);
        matcher.addURI(authority, PTContract.PATH_FAMILY + "/*", FAMILY_BY_FAM_CODE);
        matcher.addURI(authority, PTContract.PATH_IND + "/*", INDIVIDUAL_BY_FAMILY);
        matcher.addURI(authority, PTContract.PATH_IND + "/*/#", INDIVIDUAL_BY_FAM_AND_IND_CODE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        _dbHelper = new PTDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = _uriMatcher.match(uri);
        switch (match) {
            case FAMILY_BY_VISIT_DAY:
                return PTContract.Fam.CONTENT_TYPE;
            case FAMILY:
                return PTContract.Fam.CONTENT_TYPE;
            case FAMILY_BY_FAM_CODE:
                return PTContract.Fam.CONTENT_ITEM_TYPE;
            case INDIVIDUAL_BY_FAMILY:
                return PTContract.Ind.CONTENT_TYPE;
            case INDIVIDUAL_BY_FAM_AND_IND_CODE:
                return PTContract.Ind.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (_uriMatcher.match(uri)) {
            case FAMILY:
                retCursor = _dbHelper.getReadableDatabase().query(PTContract.Fam.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FAMILY_BY_VISIT_DAY:
                retCursor = getFamilyListByVisitDay(projection, selectionArgs, sortOrder);
                break;
            case FAMILY_BY_FAM_CODE:
                retCursor = getFamilyByFamCode(projection, selectionArgs, sortOrder);
                break;
            case INDIVIDUAL_BY_FAMILY:
                retCursor = getIndividualsByFamCode(projection, selectionArgs, sortOrder);
                break;
            case INDIVIDUAL_BY_FAM_AND_IND_CODE:
                retCursor = getIndividualByFamAndIndCode(projection, selectionArgs, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = _dbHelper.getWritableDatabase();
        final int match = _uriMatcher.match(uri);
        Uri returnUri;
        long id;

        switch (match) {
            case FAMILY:
                id = db.insert(PTContract.Fam.TABLE_NAME, null, contentValues);
                if (id > 0)
                    returnUri = PTContract.Fam.buildFamilyUri(id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            case INDIVIDUAL:
                id = db.insert(PTContract.Ind.TABLE_NAME, null, contentValues);
                if (id > 0)
                    returnUri = PTContract.Ind.buildIndUri(id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = _dbHelper.getWritableDatabase();
        final int match = _uriMatcher.match(uri);
        long id;
        int returnCount = 0;

        switch (match) {
            case FAMILY:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        id = db.insert(PTContract.Fam.TABLE_NAME, null, value);
                        if (id != -1)
                            returnCount++;
                    }
                }
                finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case INDIVIDUAL:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        id = db.insert(PTContract.Ind.TABLE_NAME, null, value);
                        if (id != -1)
                            returnCount++;
                    }
                }
                finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public void shutdown() {
        _dbHelper.close();
        super.shutdown();
    }
}
