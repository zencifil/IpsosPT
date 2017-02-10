package com.ipsos.cpm.ipsospt.model;

public class PanelWeek {

    public final String CountryCode;
    public final String PanelType;
    public final int WeekCode;
    public final String WeekDesc;
    public final String StartDate;
    public final String EndDate;
    public final int Active;

    PanelWeek(String countryCode, String panelType, int weekCode, String weekDesc,
              String startDate, String endDate, int active) {
        this.CountryCode = countryCode;
        this.PanelType = panelType;
        this.WeekCode = weekCode;
        this.WeekDesc = weekDesc;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.Active = active;
    }
}
