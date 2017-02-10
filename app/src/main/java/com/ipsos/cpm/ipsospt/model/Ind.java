package com.ipsos.cpm.ipsospt.model;

public class Ind {

    public final String CountryCode;
    public final String FamCode;
    public final int IndCode;
    public final String IndName;
    public final String DoB;
    public final String Phone;
    public final String Phone2;
    public final String Email;
    public final String Email2;
    public final int SP;
    public final int ALK;
    public final int IsUser;
    public final int Active;

    Ind(String countryCode, String famCode, int indCode, String indName, String dob,
            String phone, String phone2, String email, String email2, int sp,
            int alk, int isUser, int active) {
        this.CountryCode = countryCode;
        this.FamCode = famCode;
        this.IndCode = indCode;
        this.IndName = indName;
        this.DoB = dob;
        this.Phone = phone;
        this.Phone2 = phone2;
        this.Email = email;
        this.Email2 = email2;
        this.SP = sp;
        this.ALK = alk;
        this.IsUser = isUser;
        this.Active = active;
    }

}
