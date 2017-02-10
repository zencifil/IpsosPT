package com.ipsos.cpm.ipsospt.model;

public class Panel {

    public final String CountryCode;
    public final String FldCode;
    public final String PanelType;
    public final String FamCode;
    public final int IndCode;
    public final int WeekCode;
    public final int WeekCheck;

    Panel(String countryCode, String fldCode, String panelType, String famCode,
          int indCode, int weekCode, int weekCheck) {
        this.CountryCode = countryCode;
        this.FldCode = fldCode;
        this.PanelType = panelType;
        this.FamCode = famCode;
        this.IndCode = indCode;
        this.WeekCode = weekCode;
        this.WeekCheck = weekCheck;
    }

}
