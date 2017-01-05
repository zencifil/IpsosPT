package com.ipsos.cpm.ipsospt.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by zencifil on 17/11/2016.
 */

public class PTContract {

    public static final String CONTENT_AUTHORITY = "com.ipsos.cpm.ipsospt";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAMILY = "FAM00";
    public static final String PATH_FLD = "FLD00";
    public static final String PATH_IND = "IND00";
    public static final String PATH_PANEL = "PANEL";
    public static final String PATH_PANEL_WEEK = "PANEL_WEEK";
    public static final String PATH_LOG = "LOG";

    public static final class Fam implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAMILY).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAMILY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAMILY;

        public static final String TABLE_NAME = "FAM00";

        public static final String COLUMN_COUNTRY_CODE = "COUNTRY_CODE";
        public static final String COLUMN_FAM_CODE = "FAM00_CODE";
        public static final String COLUMN_FAM_NAME = "FAM00_NAME";
        public static final String COLUMN_REG_BEG = "FAM00_REG_BEG";
        public static final String COLUMN_DISTRICT = "FAM00_DISTRICT";
        public static final String COLUMN_PROVINCE = "FAM00_PROVINCE";
        public static final String COLUMN_NEIGHBORHOOD = "FAM00_NEIGHBORHOOD";
        public static final String COLUMN_ADDRESS = "FAM00_ADDRESS";
        public static final String COLUMN_LANDLINE = "FAM00_LANDLINE";
        public static final String COLUMN_WORKLINE = "FAM00_WORKLINE";
        public static final String COLUMN_CELLULAR = "FAM00_CELLULAR";
        public static final String COLUMN_FLD_CODE = "FAM00_FLD00_CODE";
        public static final String COLUMN_VISIT_DAY = "FAM00_VISIT_DAY";
        public static final String COLUMN_AVP = "FAM00_AVP";
        public static final String COLUMN_ALK = "FAM00_ALK";
        public static final String COLUMN_SP = "FAM00_SP";
        public static final String COLUMN_EK_ALK = "FAM00_EK_ALK";
        public static final String COLUMN_BABY = "FAM00_BABY";
        public static final String COLUMN_POINT = "FAM00_POINT";
        public static final String COLUMN_ISUSER = "FAM00_ISUSER";
        public static final String COLUMN_ACTIVE = "FAM00_ACTIVE";
        public static final String COLUMN_SYNC = "FAM00_SYNC";
        public static final String COLUMN_SYNC_DATE = "FAM00_SYNC_DATE";

        public static Uri buildFamilyUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildFamilyUriWithFamCode(String famCode) {
            return CONTENT_URI.buildUpon().appendPath(famCode).build();
        }

        public static Uri buildFamilyUriForDay(int day) {
            if (day == 0)
                return CONTENT_URI.buildUpon().build();
            else
                return CONTENT_URI.buildUpon().appendPath(Integer.toString(day)).build();
        }

        public static String getFamCodeFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static int getVisitDayFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }
    }

    public static final class Fld implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FLD).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FLD;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FLD;

        public static final String TABLE_NAME = "FLD00";

        public static final String COLUMN_COUNTRY_CODE = "COUNTRY_CODE";
        public static final String COLUMN_FLD_CODE = "FLD00_CODE";
        public static final String COLUMN_FLD_NAME = "FLD00_NAMESURNAME";
        public static final String COLUMN_PROVINCE = "FLD00_PROVINCE";
        public static final String COLUMN_REGION = "FLD00_REGION";
        public static final String COLUMN_PHONE = "FLD00_PHONE";
        public static final String COLUMN_PHONE2 = "FLD00_PHONE2";
        public static final String COLUMN_EMAIL = "FLD00_EMAIL";
        public static final String COLUMN_EMAIL2 = "FLD00_EMAIL2";
        public static final String COLUMN_ISUSER = "FLD00_ISUSER";
        public static final String COLUMN_ACTIVE = "FLD00_ACTIVE";
        public static final String COLUMN_SYNC = "FLD00_SYNC";
        public static final String COLUMN_SYNC_DATE = "FLD00_SYNC_DATE";

        public static Uri buildFldUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class Ind implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_IND).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IND;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IND;

        public static final String TABLE_NAME = "IND00";

        public static final String COLUMN_COUNTRY_CODE = "COUNTRY_CODE";
        public static final String COLUMN_FAM_CODE = "FAM00_CODE";
        public static final String COLUMN_IND_CODE = "IND00_CODE";
        public static final String COLUMN_IND_NAME = "IND00_NAME";
        public static final String COLUMN_DOB = "IND00_DOB";
        public static final String COLUMN_PHONE = "IND00_PHONE";
        public static final String COLUMN_PHONE2 = "IND00_PHONE2";
        public static final String COLUMN_EMAIL = "IND00_EMAIL";
        public static final String COLUMN_EMAIL2 = "IND00_EMAIL2";
        public static final String COLUMN_SP = "IND00_SP"; //sigara
        public static final String COLUMN_ALK = "IND00_ALK"; //alkol gunluk
        public static final String COLUMN_ISUSER = "IND00_ISUSER";
        public static final String COLUMN_ACTIVE = "IND00_ACTIVE";
        public static final String COLUMN_SYNC = "IND00_SYNC";
        public static final String COLUMN_SYNC_DATE = "IND00_SYNC_DATE";

        public static Uri buildIndUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildIndUriWithFamCode(String famCode) {
            return CONTENT_URI.buildUpon().appendPath(famCode).build();
        }

        public static Uri buildIndUriFromFamUri(Uri famUri) {
            String famCode = Fam.getFamCodeFromUri(famUri);
            return buildIndUriWithFamCode(famCode);
        }

        public static int getIndCodeFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(2));
        }
    }

    public static final class Panel implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PANEL).build();
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PANEL;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PANEL;

        public static final String TABLE_NAME = "PANEL";

        public static final String COLUMN_COUNTRY_CODE = "COUNTRY_CODE";
        public static final String COLUMN_FLD_CODE = "FLD00_CODE";
        public static final String COLUMN_PANEL_TYPE = "PANEL_TYPE";
        public static final String COLUMN_FAM_CODE = "FAM00_CODE";
        public static final String COLUMN_IND_CODE = "IND00_CODE";
        public static final String COLUMN_WEEK_CODE = "WEEK_CODE";
        public static final String COLUMN_WEEK_CHECK = "WEEK_CHECK";
        public static final String COLUMN_SYNC = "PANEL_SYNC";
        public static final String COLUMN_SYNC_DATE = "PANEL_SYNC_DATE";

        public static Uri buildPanelUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildPanelUriWithPanelTypeFamCodeAndWeekCode(String panelType, String famCode, int weekCode) {
            return CONTENT_URI.buildUpon().appendPath(panelType).appendPath(famCode).appendPath(Integer.toString(weekCode)).build();
        }

        public static String getPanelTypeFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getFamCodeFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static int getWeekCodeFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(3));
        }
    }

    public static final class PanelWeek implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PANEL_WEEK).build();
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PANEL_WEEK;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PANEL_WEEK;

        public static final String TABLE_NAME = "PANEL_WEEK";

        public static final String COLUMN_COUNTRY_CODE = "COUNTRY_CODE";
        public static final String COLUMN_PANEL_TYPE = "PANEL_TYPE";
        public static final String COLUMN_WEEK_CODE = "WEEK_CODE";
        public static final String COLUMN_WEEK_DESC = "WEEK_DESC";
        public static final String COLUMN_START_DATE = "START_DATE";
        public static final String COLUMN_END_DATE = "END_DATE";
        public static final String COLUMN_ACTIVE = "ACTIVE";
        public static final String COLUMN_SYNC = "PANEL_WEEK_SYNC";
        public static final String COLUMN_SYNC_DATE = "PANEL_WEEK_SYNC_DATE";

        public static Uri buildPanelsWeeksUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildPanelsUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildWeeksUri(String panelType) {
            return CONTENT_URI.buildUpon().appendPath(panelType).build();
        }

        public static String getPanelTypeFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class Log implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOG).build();
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOG;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOG;

        public static final String TABLE_NAME = "LOG";

        public static final String COLUMN_COUNTRY_CODE = "COUNTRY_CODE";
        public static final String COLUMN_LOG_TYPE = "LOG_TYPE";
        public static final String COLUMN_LOG_MESSAGE = "LOG_MESSAGE";
        public static final String COLUMN_LOG_DATE = "LOG_DATE";
        public static final String COLUMN_VERSION = "VERSION";
        public static final String COLUMN_USER = "USER";
        public static final String COLUMN_ACTIVITY = "ACTIVITY";
        public static final String COLUMN_SYNC = "LOG_SYNC";
        public static final String COLUMN_SYNC_DATE = "LOG_SYNC_DATE";

        public static Uri buildLogUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
