package com.ayprojects.helpinghands.models;

import org.springframework.data.annotation.Transient;

import java.util.List;

public class NameAndTranslationsObj {
    public String defaultName;
    public List<LangValueObj> translations;
    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public List<LangValueObj> getTranslations() {
        return translations;
    }

    public void setTranslations(List<LangValueObj> translations) {
        this.translations = translations;
    }
}
