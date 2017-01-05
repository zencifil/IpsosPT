package com.ipsos.cpm.ipsospt.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class PTProvider extends ContentProvider {

    private String LOG_TAG = PTProvider.class.getSimpleName();
    private PTDbHelper _dbHelper;
    private static final UriMatcher _uriMatcher = buildUriMatcher();
    static final int FAMILY = 100;
    static final int FAMILY_BY_VISIT_DAY = 101;
    static final int FAMILY_BY_FAM_CODE = 102;
    static final int INDIVIDUAL = 200;
    static final int INDIVIDUAL_BY_FAMILY = 201;
    static final int INDIVIDUAL_BY_FAM_AND_IND_CODE = 202;
    static final int PANEL = 300;
    static final int PANEL_BY_PANELTYPE_FAMCODE_AND_WEEKCODE = 301;
    static final int PANEL_WEEK = 400;
    static final int PANEL_WEEK_BY_PANEL_TYPE = 401;
    static final int LOG = 500;

    private static final SQLiteQueryBuilder _famQueryBuilder;
    private static final SQLiteQueryBuilder _indQueryBuilder;
    private static final SQLiteQueryBuilder _fldQueryBuilder;
    private static final SQLiteQueryBuilder _panelQueryBuilder;
    private static final SQLiteQueryBuilder _panelWeekQueryBuilder;
    private static final SQLiteQueryBuilder _logQueryBuilder;
    static {
        _famQueryBuilder = new SQLiteQueryBuilder();
        _indQueryBuilder = new SQLiteQueryBuilder();
        _fldQueryBuilder = new SQLiteQueryBuilder();
        _panelQueryBuilder = new SQLiteQueryBuilder();
        _panelWeekQueryBuilder = new SQLiteQueryBuilder();
        _logQueryBuilder = new SQLiteQueryBuilder();

        _famQueryBuilder.setTables(PTContract.Fam.TABLE_NAME);
        _indQueryBuilder.setTables(PTContract.Ind.TABLE_NAME);
        _fldQueryBuilder.setTables(PTContract.Fld.TABLE_NAME);
        _panelQueryBuilder.setTables(PTContract.Panel.TABLE_NAME +
                    " INNER JOIN " + PTContract.PanelWeek.TABLE_NAME + " ON " +
                PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_PANEL_TYPE + " = " +
                PTContract.PanelWeek.TABLE_NAME + "." + PTContract.PanelWeek.COLUMN_PANEL_TYPE + " AND " +
                PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_WEEK_CODE + " = " +
                PTContract.PanelWeek.TABLE_NAME + "." + PTContract.PanelWeek.COLUMN_WEEK_CODE +
                    " INNER JOIN " + PTContract.Ind.TABLE_NAME + " ON " +
                PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_FAM_CODE + " = " +
                PTContract.Ind.TABLE_NAME + "." + PTContract.Ind.COLUMN_FAM_CODE + " AND " +
                PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_IND_CODE + " = " +
                PTContract.Ind.TABLE_NAME + "." + PTContract.Ind.COLUMN_IND_NAME );
        _panelWeekQueryBuilder.setTables(PTContract.PanelWeek.TABLE_NAME);
        _logQueryBuilder.setTables(PTContract.Log.TABLE_NAME);
    }

    private static final String _familyByVisitDaySelection =
            PTContract.Fam.TABLE_NAME + "." + PTContract.Fam.COLUMN_VISIT_DAY + " = ? ";
    private static final String _familyByFamCodeSelection =
            PTContract.Fam.TABLE_NAME + "." + PTContract.Fam.COLUMN_FAM_CODE + " = ? ";

    private static final String _individualsByFamCodeSelection =
            PTContract.Ind.TABLE_NAME + "." + PTContract.Ind.COLUMN_FAM_CODE + " = ? ";
    private static final String _individualByFamAndIndCodeSelection =
            PTContract.Ind.TABLE_NAME + "." + PTContract.Ind.COLUMN_FAM_CODE + " = ? AND " +
            PTContract.Ind.TABLE_NAME + "." + PTContract.Ind.COLUMN_IND_CODE + " = ? ";

    private static final String _panelByPanelTypeFamCodeAndWeekCodeSelection =
            PTContract.Panel.COLUMN_PANEL_TYPE + " = ? AND " +
            PTContract.Panel.COLUMN_FAM_CODE + " = ? AND " +
            PTContract.Panel.COLUMN_WEEK_CODE + " = ? ";

    private static final String _panelWeekPanelSelection =
            PTContract.PanelWeek.TABLE_NAME + "." + PTContract.PanelWeek.COLUMN_ACTIVE + " = 1 ";
    private static final String _panelWeekWeekSelection =
            PTContract.PanelWeek.TABLE_NAME + "." + PTContract.PanelWeek.COLUMN_PANEL_TYPE + " = ? ";

    private static final String _shippingSelection =
            PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_WEEK_CHECK + " = 2 ";

    private Cursor getFamilyListByVisitDay(Uri uri, String[] projection, String sortOrder) {
        String selection = _familyByVisitDaySelection;
        int visitDay = PTContract.Fam.getVisitDayFromUri(uri);
        return _famQueryBuilder.query(_dbHelper.getReadableDatabase(), projection, selection,
                new String[]{Integer.toString(visitDay)}, null, null, sortOrder);
    }

    private Cursor getFamilyByFamCode(Uri uri, String[] projection, String sortOrder) {
        String selection = _familyByFamCodeSelection;
        String famCode = PTContract.Fam.getFamCodeFromUri(uri);
        return _famQueryBuilder.query(_dbHelper.getReadableDatabase(), projection, selection,
                new String[]{famCode}, null, null, sortOrder);
    }

    private Cursor getIndividualsByFamCode(Uri uri, String sortOrder) {
        String selection = _individualsByFamCodeSelection;
        String famCode = PTContract.Fam.getFamCodeFromUri(uri);
        String[] projection = new String[] { PTContract.Ind._ID, PTContract.Ind.COLUMN_FAM_CODE,
                PTContract.Ind.COLUMN_IND_CODE, PTContract.Ind.COLUMN_IND_NAME};
        return _indQueryBuilder.query(_dbHelper.getReadableDatabase(), projection, selection,
                new String[]{famCode}, null, null, sortOrder);
    }

    private Cursor getIndividualByFamAndIndCode(Uri uri, String sortOrder) {
        String selection = _individualByFamAndIndCodeSelection;
        String famCode = PTContract.Fam.getFamCodeFromUri(uri);
        int indCode = PTContract.Ind.getIndCodeFromUri(uri);
        String[] projection = new String[] { PTContract.Ind._ID, PTContract.Ind.COLUMN_FAM_CODE,
                PTContract.Ind.COLUMN_IND_CODE, PTContract.Ind.COLUMN_IND_NAME};
        return _indQueryBuilder.query(_dbHelper.getReadableDatabase(), projection, selection,
                new String[]{famCode, Integer.toString(indCode)}, null, null, sortOrder);
    }

    @Nullable
    private Cursor getPanelByPanelTypeFamCodeAndWeekCode(Uri uri, String sortOrder) {
        String panelType = PTContract.Panel.getPanelTypeFromUri(uri);
        String famCode = PTContract.Panel.getFamCodeFromUri(uri);
        int weekCode = PTContract.Panel.getWeekCodeFromUri(uri);
        if (panelType == null || famCode == null || panelType.equals("null") || famCode.equals("null"))
            return null;
        String[] projection = new String[] { PTContract.Panel._ID, PTContract.Panel.COLUMN_PANEL_TYPE,
                PTContract.Panel.COLUMN_FAM_CODE, PTContract.Panel.COLUMN_IND_CODE, PTContract.Ind.COLUMN_IND_NAME,
                PTContract.Panel.COLUMN_WEEK_CODE, PTContract.Panel.COLUMN_WEEK_CHECK };
        return _panelQueryBuilder.query(_dbHelper.getReadableDatabase(), projection, _panelByPanelTypeFamCodeAndWeekCodeSelection,
                new String[] {panelType, famCode, Integer.toString(weekCode)}, null, null, sortOrder);
    }

    private Cursor getPanelTypes(Uri uri, String sortOrder) {
        //TODO famCode may be added as parameter
        String selection = _panelWeekPanelSelection;
        String[] projection = new String[] { PTContract.PanelWeek._ID, PTContract.PanelWeek.COLUMN_PANEL_TYPE };
        return _panelWeekQueryBuilder.query(_dbHelper.getReadableDatabase(), projection, selection,
                null, PTContract.PanelWeek.COLUMN_PANEL_TYPE, null, sortOrder);
    }

    private Cursor getPanelWeeks(Uri uri, String sortOrder) {
        String selection = _panelWeekWeekSelection;
        String[] projection = new String[] { PTContract.PanelWeek._ID, PTContract.PanelWeek.COLUMN_WEEK_CODE,
                PTContract.PanelWeek.COLUMN_WEEK_DESC };
        String panelType = PTContract.PanelWeek.getPanelTypeFromUri(uri);
        return _panelWeekQueryBuilder.query(_dbHelper.getReadableDatabase(), projection, selection,
                new String[] {panelType}, null, null, sortOrder);
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PTContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, PTContract.PATH_FAMILY, FAMILY);
        matcher.addURI(authority, PTContract.PATH_FAMILY + "/#", FAMILY_BY_VISIT_DAY);
        matcher.addURI(authority, PTContract.PATH_FAMILY + "/*", FAMILY_BY_FAM_CODE);
        matcher.addURI(authority, PTContract.PATH_IND + "/*", INDIVIDUAL_BY_FAMILY);
        matcher.addURI(authority, PTContract.PATH_IND + "/*/#", INDIVIDUAL_BY_FAM_AND_IND_CODE);
        matcher.addURI(authority, PTContract.PATH_PANEL, PANEL);
        matcher.addURI(authority, PTContract.PATH_PANEL + "/*/*/#", PANEL_BY_PANELTYPE_FAMCODE_AND_WEEKCODE);
        matcher.addURI(authority, PTContract.PATH_PANEL_WEEK, PANEL_WEEK);
        matcher.addURI(authority, PTContract.PATH_PANEL_WEEK + "/*", PANEL_WEEK_BY_PANEL_TYPE);
        matcher.addURI(authority, PTContract.PATH_LOG, LOG);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        _dbHelper = new PTDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = _uriMatcher.match(uri);
        switch (match) {
            case FAMILY_BY_VISIT_DAY:
                return PTContract.Fam.CONTENT_DIR_TYPE;
            case FAMILY:
                return PTContract.Fam.CONTENT_DIR_TYPE;
            case FAMILY_BY_FAM_CODE:
                return PTContract.Fam.CONTENT_ITEM_TYPE;
            case INDIVIDUAL_BY_FAMILY:
                return PTContract.Ind.CONTENT_DIR_TYPE;
            case INDIVIDUAL_BY_FAM_AND_IND_CODE:
                return PTContract.Ind.CONTENT_ITEM_TYPE;
            case PANEL_BY_PANELTYPE_FAMCODE_AND_WEEKCODE:
                return PTContract.Panel.CONTENT_DIR_TYPE;
            case PANEL_WEEK:
                return PTContract.PanelWeek.CONTENT_DIR_TYPE;
            case PANEL_WEEK_BY_PANEL_TYPE:
                return PTContract.PanelWeek.CONTENT_DIR_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (_uriMatcher.match(uri)) {
            case FAMILY:
                retCursor = _dbHelper.getReadableDatabase().query(PTContract.Fam.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case FAMILY_BY_VISIT_DAY:
                retCursor = getFamilyListByVisitDay(uri, projection, sortOrder);
                break;
            case FAMILY_BY_FAM_CODE:
                retCursor = getFamilyByFamCode(uri, projection, sortOrder);
                break;
            case INDIVIDUAL_BY_FAMILY:
                retCursor = getIndividualsByFamCode(uri, sortOrder);
                break;
            case INDIVIDUAL_BY_FAM_AND_IND_CODE:
                retCursor = getIndividualByFamAndIndCode(uri, sortOrder);
                break;
            case PANEL_BY_PANELTYPE_FAMCODE_AND_WEEKCODE:
                retCursor = getPanelByPanelTypeFamCodeAndWeekCode(uri, sortOrder);
                break;
            case PANEL_WEEK:
                retCursor = getPanelTypes(uri, sortOrder);
                break;
            case PANEL_WEEK_BY_PANEL_TYPE:
                retCursor = getPanelWeeks(uri, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
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
            case PANEL:
                id = db.insert(PTContract.Panel.TABLE_NAME, null, contentValues);
                if (id > 0)
                    returnUri = PTContract.Panel.buildPanelUri(id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            case PANEL_WEEK:
                id = db.insert(PTContract.PanelWeek.TABLE_NAME, null, contentValues);
                if (id > 0)
                    returnUri = PTContract.PanelWeek.buildPanelsWeeksUri(id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            case LOG:
                id = db.insert(PTContract.Log.TABLE_NAME, null, contentValues);
                if (id > 0)
                    returnUri = PTContract.Log.buildLogUri(id);
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
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
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
                break;
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
                break;
            case PANEL:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        id = db.insert(PTContract.Panel.TABLE_NAME, null, value);
                        if (id != -1)
                            returnCount++;
                    }
                }
                finally {
                    db.endTransaction();
                }
                break;
            case PANEL_WEEK:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        id = db.insert(PTContract.PanelWeek.TABLE_NAME, null, value);
                        if (id != -1)
                            returnCount++;
                    }
                }
                finally {
                    db.endTransaction();
                }
                break;
            default:
                returnCount = super.bulkInsert(uri, values);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        final SQLiteDatabase db = _dbHelper.getWritableDatabase();
        final int match = _uriMatcher.match(uri);
        int rowsDeleted;

        try {
            switch (match) {
                case PANEL:
                    rowsDeleted = db.delete(PTContract.Panel.TABLE_NAME, s, strings);
                    break;
                default:
                    rowsDeleted = -1;
                    break;
            }
            if (rowsDeleted > 0)
                getContext().getContentResolver().notifyChange(uri, null);
        }
        catch (Exception ex) {
            rowsDeleted = -1;
            Log.e(LOG_TAG, ex.getMessage());
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        final SQLiteDatabase db = _dbHelper.getWritableDatabase();
        final int match = _uriMatcher.match(uri);
        int rowsUpdated;

        try {
            switch (match) {
                case PANEL:
                    rowsUpdated = db.update(PTContract.Panel.TABLE_NAME, contentValues, s, strings);
                    break;
                default:
                    rowsUpdated = -1;
                    break;
            }
            if (rowsUpdated > 0)
                getContext().getContentResolver().notifyChange(uri, null);
        }
        catch (Exception ex) {
            rowsUpdated = -1;
            Log.e(LOG_TAG, ex.getMessage());
        }
        return rowsUpdated;
    }

    @Override
    public void shutdown() {
        _dbHelper.close();
        super.shutdown();
    }
}
