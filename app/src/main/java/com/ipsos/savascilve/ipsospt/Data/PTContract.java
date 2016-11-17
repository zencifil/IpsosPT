package com.ipsos.savascilve.ipsospt.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by zencifil on 17/11/2016.
 */

public class PTContract {

    public static final String CONTENT_AUTHORITY = "com.ipsos.savascilve.ipsospt.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAMILY = "family";
    public static final String PATH_FLD = "fld";
    public static final String PATH_IND = "ind";

    public static final class Family implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAMILY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAMILY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAMILY;

        public static final String TABLE_NAME = "family";

        public static final String COLUMN_FAM_CODE = "fam_code";
        public static final String COLUMN_FAM_NAME = "fam_name";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_TOWN = "town";
        public static final String COLUMN_DISTRICT = "district";
        public static final String COLUMN_STREET = "street";
        public static final String COLUMN_ROAD = "road";
        public static final String COLUMN_HOUSE_NO = "house_no";
        public static final String COLUMN_DOOR_NO = "door_no";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_PHONE2 = "phone2";
        public static final String COLUMN_FLD_CODE = "fld_code";
        public static final String COLUMN_VISIT_DATE = "visit_date";
        public static final String COLUMN_AVP = "avp";
        public static final String COLUMN_SP = "sp";
        public static final String COLUMN_ALK = "alk";
        public static final String COLUMN_BABY = "baby";
        public static final String COLUMN_HP = "hp";
        public static final String COLUMN_EK_ALK = "ek_alk";
        public static final String COLUMN_ACTIVE = "active";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class Fld implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FLD).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FLD;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FLD;

        public static final String TABLE_NAME = "fld";

        public static final String COLUMN_FLD_CODE = "fld_code";
        public static final String COLUMN_FLD_NAME = "fld_name";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_PHONE2 = "phone2";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_EMAIL2 = "email2";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class Ind implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_IND).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IND;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IND;

        public static final String TABLE_NAME = "ind";

        public static final String COLUMN_FAM_CODE = "fam_code";
        public static final String COLUMN_IND_CODE = "ind_code";
        public static final String COLUMN_IND_NAME = "ind_name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_PHONE2 = "phone2";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_EMAIL2 = "email2";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
