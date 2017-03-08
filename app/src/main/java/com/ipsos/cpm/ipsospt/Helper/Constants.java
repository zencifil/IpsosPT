package com.ipsos.cpm.ipsospt.helper;

public class Constants {

    public static final int NETWORK_TIMEOUT = 30000; //in milliseconds
    public static final String BASE_URL = "https://gpc.ipsos.com.tr/";
    public static final String EXTRA_FLDNAME = "com.ipsos.cpm.ipsospt.FLDNAME";
    public static final String EXTRA_FLDCODE = "com.ipsos.cpm.ipsospt.FLDCODE";
    public static final String EXTRA_EMAIL = "com.ipsos.cpm.ipsospt.EMAIL";
    public static final String EXTRA_FAMCODE = "com.ipsos.cpm.ipsospt.FAMCODE";
    public static final String EXTRA_FAMNAME = "com.ipsos.cpm.ipsospt.FAMNAME";
    public static final String PREF_NAME = "IpsosPref";
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FLD_NAME = "fld_name";
    public static final String KEY_FLD_EMAIL = "fld_email";
    public static final String KEY_FLD_CODE = "fld_code";
    public static final String KEY_SYNC_STATUS = "sync_status";
    public static final int SHIPPED_FLAG = 3;
    public static final String PHONE_NUMBER = "05359348602";
    public static final String[] EMAIL_TO_LIST = new String[] { "savas.cilve@ipsos.com" };
    public static final String EMAIL_SUBJECT = "PT: {0}";
    public static final String LINK_TO_NEW_FORM = "http://www.google.com";
    public static final int AUTHKEY_VALID_DAY = 60;

    public static final String API_LOGIN = "token";
    public static final String API_FAM = "api/FAM00";
    public static final String API_IND = "api/IND00";
    public static final String API_PANEL = "api/PANEL";
    public static final String API_PANEL_WEEK = "api/PANEL_WEEK";

    public static final String QUERY_PARAM_USERNAME = "username";
    public static final String QUERY_PARAM_PASSWORD = "password";
    public static final String QUERY_PARAM_GRANT_TYPE = "grant_type";
    public static final String QUERY_PARAM_TOKEN = "token";

    public static final String QUERY_PARAM_COUNTRY_CODE = "COUNTRY_CODE";
    public static final String QUERY_PARAM_FLD_CODE = "FLD00_CODE";
    public static final String QUERY_PARAM_PANEL_TYPE = "PANEL_TYPE";
    public static final String QUERY_PARAM_FAM_CODE = "FAM00_CODE";
    public static final String QUERY_PARAM_IND_CODE = "IND00";
    public static final String QUERY_PARAM_WEEK_CODE = "WEEK_CODE";

    public static final String JSON_AUTH_KEY_TOKEN = "access_token";
    public static final String JSON_ERROR_DESCRIPTION = "error_description";
    // 60 seconds * 60 minutes * 24 hours = one day
    public static final int SYNC_INTERVAL = 10 * 60; // 60 * 60 * 24;
    //public static final int SYNC_INTERVAL = 6;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
}
