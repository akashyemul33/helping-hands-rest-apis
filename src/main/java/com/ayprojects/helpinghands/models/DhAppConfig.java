package com.ayprojects.helpinghands.models;

public class DhAppConfig extends AllCommonUsedAttributes {
    private int appConfigId;
    private boolean languageSupport;
    private boolean darkThemeSupport;
    private boolean showImgToCategories;
    private boolean showImgToProducts;
    private String currentAppVersion;
    private Languages languages;
    private Modules modules;

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