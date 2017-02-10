package com.ipsos.cpm.ipsospt.model;

public class Fam {
    public final String CountryCode;
    public final String FamCode;
    public final String FamName;
    public final String RegBeg;
    public final String District;
    public final String Province;
    public final String Neighborhood;
    public final String Address;
    public final String Landline;
    public final String Workline;
    public final String Cellular;
    public final String FldCode;
    public final int VisitDay;
    public final int AVP;
    public final int SP;
    public final int ALK;
    public final int EK_ALK;
    public final int BABY;
    public final int Point;

    Fam(String countryCode, String famCode, String famName, String regBeg, String district,
            String province, String neighborhood, String address, String landline,
            String workline, String cellular, String fldCode, int visitDay,
            int avp, int sp, int alk, int ekAlk, int baby, int point) {
        this.CountryCode = countryCode;
        this.FamCode = famCode;
        this.FamName = famName;
        this.RegBeg = regBeg;
        this.District = district;
        this.Province = province;
        this.Neighborhood = neighborhood;
        this.Address = address;
        this.Landline = landline;
        this.Workline = workline;
        this.Cellular = cellular;
        this.FldCode = fldCode;
        this.VisitDay = visitDay;
        this.AVP = avp;
        this.SP = sp;
        this.ALK = alk;
        this.EK_ALK = ekAlk;
        this.BABY = baby;
        this.Point = point;
    }
}
