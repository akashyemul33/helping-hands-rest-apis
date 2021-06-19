package com.ayprojects.helpinghands.models;

import java.util.List;

public class DhAppConfig extends AllCommonUsedAttributes {
    private int appConfigId;
    private boolean languageSupport;
    private boolean darkThemeSupport;
    private boolean showImgToCategories;
    private boolean showImgToProducts;
    private String currentAppVersion;
    private Languages languages;
    private String addedBy;
    private Modules modules;
    private List<LangValueObj> units;

    public List<LangValueObj> getOfflineMessages() {
        return offlineMessages;
    }

    public void setOfflineMessages(List<LangValueObj> offlineMessages) {
        this.offlineMessages = offlineMessages;
    }

    private List<LangValueObj> offlineMessages;
    private ThoughtsConfig thoughtsConfig;

    public ThoughtsConfig getThoughtsConfig() {
        return thoughtsConfig;
    }

    public void setThoughtsConfig(ThoughtsConfig thoughtsConfig) {
        this.thoughtsConfig = thoughtsConfig;
    }

    public DhAppConfig() {

    }

    public List<LangValueObj> getUnits() {
        return units;
    }

    public void setUnits(List<LangValueObj> units) {
        this.units = units;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public int getAppConfigId() {
        return appConfigId;
    }

    public void setAppConfigId(int appConfigId) {
        this.appConfigId = appConfigId;
    }

    public Languages getLanguages() {
        return languages;
    }

    public void setLanguages(Languages languages) {
        this.languages = languages;
    }

    public Modules getModules() {
        return modules;
    }

    public void setModules(Modules modules) {
        this.modules = modules;
    }

    public DhAppConfig(int appConfigId, boolean languageSupport, boolean darkThemeSupport, boolean showImgToCategories, boolean showImgToProducts, String currentAppVersion, Languages languages, Modules modules) {
        this.appConfigId = appConfigId;
        this.languageSupport = languageSupport;
        this.darkThemeSupport = darkThemeSupport;
        this.showImgToCategories = showImgToCategories;
        this.showImgToProducts = showImgToProducts;
        this.currentAppVersion = currentAppVersion;
        this.languages = languages;
        this.modules = modules;
    }

    public boolean isLanguageSupport() {
        return languageSupport;
    }

    public void setLanguageSupport(boolean languageSupport) {
        this.languageSupport = languageSupport;
    }

    public boolean isDarkThemeSupport() {
        return darkThemeSupport;
    }

    public void setDarkThemeSupport(boolean darkThemeSupport) {
        this.darkThemeSupport = darkThemeSupport;
    }

    public boolean isShowImgToCategories() {
        return showImgToCategories;
    }

    public void setShowImgToCategories(boolean showImgToCategories) {
        this.showImgToCategories = showImgToCategories;
    }

    public boolean isShowImgToProducts() {
        return showImgToProducts;
    }

    public void setShowImgToProducts(boolean showImgToProducts) {
        this.showImgToProducts = showImgToProducts;
    }

    public String getCurrentAppVersion() {
        return currentAppVersion;
    }

    public void setCurrentAppVersion(String currentAppVersion) {
        this.currentAppVersion = currentAppVersion;
    }
}
class Languages{
    private boolean english;
    private boolean marathi;
    private boolean hindi;
    private boolean kannada;
    private boolean gujrati;
    private boolean telugu;

    public boolean isKannada() {
        return kannada;
    }

    public void setKannada(boolean kannada) {
        this.kannada = kannada;
    }

    public boolean isGujrati() {
        return gujrati;
    }

    public void setGujrati(boolean gujrati) {
        this.gujrati = gujrati;
    }

    public boolean isTelugu() {
        return telugu;
    }

    public void setTelugu(boolean telugu) {
        this.telugu = telugu;
    }

    public boolean isEnglish() {
        return english;
    }

    public void setEnglish(boolean english) {
        this.english = english;
    }

    public boolean isMarathi() {
        return marathi;
    }

    public void setMarathi(boolean marathi) {
        this.marathi = marathi;
    }

    public boolean isHindi() {
        return hindi;
    }

    public void setHindi(boolean hindi) {
        this.hindi = hindi;
    }
}
class Modules{
    private boolean addBusinessPlace;
    private boolean addPublicPlace;
    private boolean addBusinessPost;
    private boolean addPublicPost;
    private boolean addRequirements;
    private boolean placeOrders;
    private boolean messages;
    private boolean hhAddPost;
    private boolean hhHelp;
    private boolean hhShowOverview;

    public boolean isAddBusinessPlace() {
        return addBusinessPlace;
    }

    public void setAddBusinessPlace(boolean addBusinessPlace) {
        this.addBusinessPlace = addBusinessPlace;
    }

    public boolean isAddPublicPlace() {
        return addPublicPlace;
    }

    public void setAddPublicPlace(boolean addPublicPlace) {
        this.addPublicPlace = addPublicPlace;
    }

    public boolean isAddBusinessPost() {
        return addBusinessPost;
    }

    public void setAddBusinessPost(boolean addBusinessPost) {
        this.addBusinessPost = addBusinessPost;
    }

    public boolean isAddPublicPost() {
        return addPublicPost;
    }

    public void setAddPublicPost(boolean addPublicPost) {
        this.addPublicPost = addPublicPost;
    }

    public boolean isAddRequirements() {
        return addRequirements;
    }

    public void setAddRequirements(boolean addRequirements) {
        this.addRequirements = addRequirements;
    }

    public boolean isPlaceOrders() {
        return placeOrders;
    }

    public void setPlaceOrders(boolean placeOrders) {
        this.placeOrders = placeOrders;
    }

    public boolean isMessages() {
        return messages;
    }

    public void setMessages(boolean messages) {
        this.messages = messages;
    }

    public boolean isHhAddPost() {
        return hhAddPost;
    }

    public void setHhAddPost(boolean hhAddPost) {
        this.hhAddPost = hhAddPost;
    }

    public boolean isHhHelp() {
        return hhHelp;
    }

    public void setHhHelp(boolean hhHelp) {
        this.hhHelp = hhHelp;
    }

    public boolean isHhShowOverview() {
        return hhShowOverview;
    }

    public void setHhShowOverview(boolean hhShowOverview) {
        this.hhShowOverview = hhShowOverview;
    }


}