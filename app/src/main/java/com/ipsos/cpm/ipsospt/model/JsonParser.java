package com.ipsos.cpm.ipsospt.model;

import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParser {
    private static final String LOG_TAG = JsonParser.class.getSimpleName();

    private static final String FAM_JSON_COUNTRY_CODE = "COUNTRY_CODE";
    private static final String FAM_JSON_FAM_CODE = "FAM00_CODE";
    private static final String FAM_JSON_FAM_NAME = "FAM00_NAME";
    private static final String FAM_JSON_REG_BEG = "FAM00_REG_BEG";
    private static final String FAM_JSON_DISTRICT = "FAM00_DISTRICT";
    private static final String FAM_JSON_PROVINCE = "FAM00_PROVINCE";
    private static final String FAM_JSON_NEIGHBORHOOD = "FAM00_NEIGHBORHOOD";
    private static final String FAM_JSON_ADDRESS = "FAM00_ADDRESS";
    private static final String FAM_JSON_LANDLINE = "FAM00_LANDLINE";
    private static final String FAM_JSON_WORKLINE = "FAM00_WORKLINE";
    private static final String FAM_JSON_CELLULAR = "FAM00_CELLULAR";
    private static final String FAM_JSON_FLD_CODE = "FLD00_CODE";
    private static final String FAM_JSON_VISIT_DAY = "FAM00_VISIT_DAY";
    private static final String FAM_JSON_AVP = "FAM00_AVP";
    private static final String FAM_JSON_ALK = "FAM00_ALK";
    private static final String FAM_JSON_SP = "FAM00_SP";
    private static final String FAM_JSON_EK_ALK = "FAM00_EK_ALK";
    private static final String FAM_JSON_BABY = "FAM00_BABY";
    private static final String FAM_JSON_POINT = "FAM00_POINT";

    private static final String IND_JSON_COUNTRY_CODE = "COUNTRY_CODE";
    private static final String IND_JSON_FAM_CODE = "FAM00_CODE";
    private static final String IND_JSON_IND_CODE = "IND00_CODE";
    private static final String IND_JSON_IND_NAME = "IND00_NAME";
    private static final String IND_JSON_DOB = "IND00_DOB";
    private static final String IND_JSON_PHONE = "IND00_PHONE";
    private static final String IND_JSON_PHONE2 = "IND00_PHONE2";
    private static final String IND_JSON_EMAIL = "IND00_EMAIL";
    private static final String IND_JSON_EMAIL2 = "IND00_EMAIL2";
    private static final String IND_JSON_SP = "IND00_SP";
    private static final String IND_JSON_ALK = "IND00_ALK";
    private static final String IND_JSON_ISUSER = "IND00_ISUSER";
    private static final String IND_JSON_ACTIVE = "IND00_ACTIVE";

    private static final String PANEL_JSON_COUNTRY_CODE = "COUNTRY_CODE";
    private static final String PANEL_JSON_FLD_CODE = "FLD00_CODE";
    private static final String PANEL_JSON_PANEL_TYPE = "PANEL_TYPE";
    private static final String PANEL_JSON_FAM_CODE = "FAM00_CODE";
    private static final String PANEL_JSON_IND_CODE = "IND00_CODE";
    private static final String PANEL_JSON_WEEK_CODE = "WEEK_CODE";
    private static final String PANEL_JSON_WEEK_CHECK = "WEEK_CHECK";

    private static final String PW_JSON_COUNTRY_CODE = "COUNTRY_CODE";
    private static final String PW_JSON_PANEL_TYPE = "PANEL_TYPE";
    private static final String PW_JSON_WEEK_CODE = "WEEK_CODE";
    private static final String PW_JSON_WEEK_DESC = "WEEK_DESC";
    private static final String PW_JSON_START_DATE = "START_DATE";
    private static final String PW_JSON_END_DATE = "END_DATE";
    private static final String PW_JSON_ACTIVE = "ACTIVE";

    public static Fam parseFam(JSONObject fam) {
        try {
            String countryCode = fam.getString(FAM_JSON_COUNTRY_CODE);
            String famCode = fam.getString(FAM_JSON_FAM_CODE);
            String famName = fam.getString(FAM_JSON_FAM_NAME);
            String reg_beg = fam.getString(FAM_JSON_REG_BEG);
            String district = fam.getString(FAM_JSON_DISTRICT);
            String province = fam.getString(FAM_JSON_PROVINCE);
            String neighborhood = fam.getString(FAM_JSON_NEIGHBORHOOD);
            String address = fam.getString(FAM_JSON_ADDRESS);
            String landline = fam.getString(FAM_JSON_LANDLINE);
            String workline = fam.getString(FAM_JSON_WORKLINE);
            String cellular = fam.getString(FAM_JSON_CELLULAR);
            String fldCode = fam.getString(FAM_JSON_FLD_CODE);
            int visitDay = fam.getInt(FAM_JSON_VISIT_DAY);
            int avp = fam.getInt(FAM_JSON_AVP);
            int sp = fam.getInt(FAM_JSON_SP);
            int alk = fam.getInt(FAM_JSON_ALK);
            int ekAlk = fam.getInt(FAM_JSON_EK_ALK);
            int baby = fam.getInt(FAM_JSON_BABY);
            int point = fam.getInt(FAM_JSON_POINT);

            return new Fam(countryCode, famCode, famName, reg_beg, district, province,
                    neighborhood, address, landline, workline, cellular, fldCode,
                    visitDay, avp, sp, alk, ekAlk, baby, point);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
            return null;
        }
    }

    public static Ind parseInd(JSONObject ind) {
        try {
            String countryCode = ind.getString(IND_JSON_COUNTRY_CODE);
            String famCode = ind.getString(IND_JSON_FAM_CODE);
            int indCode = ind.getInt(IND_JSON_IND_CODE);
            String indName = ind.getString(IND_JSON_IND_NAME);
            String dob = ind.getString(IND_JSON_DOB);
            String phone = ind.getString(IND_JSON_PHONE);
            String phone2 = ind.getString(IND_JSON_PHONE2);
            String email = ind.getString(IND_JSON_EMAIL);
            String email2 = ind.getString(IND_JSON_EMAIL2);
            int sp = ind.getInt(IND_JSON_SP);
            int alk = ind.getInt(IND_JSON_ALK);
            int isUser = ind.getInt(IND_JSON_ISUSER);
            int active = ind.getInt(IND_JSON_ACTIVE);

            return new Ind(countryCode, famCode, indCode, indName, dob, phone, phone2,
                    email, email2, sp, alk, isUser, active);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
            return null;
        }
    }

    public static Panel parsePanel(JSONObject panel) {
        try {
            String countryCode = panel.getString(PANEL_JSON_COUNTRY_CODE);
            String fldCode = panel.getString(PANEL_JSON_FLD_CODE);
            String panelType = panel.getString(PANEL_JSON_PANEL_TYPE);
            String famCode = panel.getString(PANEL_JSON_FAM_CODE);
            int indCode = panel.getInt(PANEL_JSON_IND_CODE);
            int weekCode = panel.getInt(PANEL_JSON_WEEK_CODE);
            int weekCheck = panel.getInt(PANEL_JSON_WEEK_CHECK);

            return new Panel(countryCode, fldCode, panelType, famCode, indCode, weekCode, weekCheck);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
            return null;
        }
    }

    public static PanelWeek parsePanelWeek(JSONObject pw) {
        try {
            String countryCode = pw.getString(PW_JSON_COUNTRY_CODE);
            String panelType = pw.getString(PW_JSON_PANEL_TYPE);
            int weekCode = pw.getInt(PW_JSON_WEEK_CODE);
            String weekDesc = pw.getString(PW_JSON_WEEK_DESC);
            String startDate = pw.getString(PW_JSON_START_DATE);
            String endDate = pw.getString(PW_JSON_END_DATE);
            int active = pw.getInt(PW_JSON_ACTIVE);

            return new PanelWeek(countryCode, panelType, weekCode, weekDesc, startDate,
                    endDate, active);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
            return null;
        }
    }

    public static JSONArray cursorToJson(Cursor cursor) {

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    }
                    catch (Exception e) {
                        Log.d(LOG_TAG, e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet;
    }
}
