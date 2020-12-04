package com.ayprojects.helpinghands.models;

public class LangValueObj {
    private String lang;
    private String value;

    public LangValueObj(String lang, String value) {
        this.lang = lang;
        this.value = value;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
