package com.ayprojects.helpinghands.models;

public class Translations {
    public String en;
    public String mr;
    public String hi;

    public Translations(String en, String mr, String hi) {
        this.en = en;
        this.mr = mr;
        this.hi = hi;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getMr() {
        return mr;
    }

    public void setMr(String mr) {
        this.mr = mr;
    }

    public String getHi() {
        return hi;
    }

    public void setHi(String hi) {
        this.hi = hi;
    }
}
