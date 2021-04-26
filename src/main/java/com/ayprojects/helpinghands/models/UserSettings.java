package com.ayprojects.helpinghands.models;

public class UserSettings {
    private long totalPlacesLimit;
    private long perDayAddPlacesLimit;
    private long perDayAddPostsLimit;
    private long perPlaceImagesLimit;
    private long perPostImagesLimit;
    private boolean perDayAddPlacesLimitEnabled;
    private boolean perDayAddPostsLimitEnabled;
    private boolean perPlaceImagesLimitEnabled;
    private boolean perPostImagesLimitEnabled;
    private boolean perProductDefaultImageLimitEnabled;
    private long perProductDefaultImagesLimit;
    private long perProductMaxImagesLimit;
    private boolean perProductMaxImagesLimitEnabled;
    private long perPlaceProductsLimit;

    public UserSettings(long totalPlacesLimit, long perDayAddPlacesLimit, long perDayAddPostsLimit, long perPlaceImagesLimit, long perPostImagesLimit, long perPlaceProductsLimit, boolean perDayAddPlacesLimitEnabled, boolean perDayAddPostsLimitEnabled, boolean perPlaceImagesLimitEnabled, boolean perPostImagesLimitEnabled, long perProductDefaultImagesLimit, boolean perProductDefaultImageLimitEnabled, boolean perProductMaxImagesLimitEnabled, long perProductMaxImagesLimit) {
        this.totalPlacesLimit = totalPlacesLimit;
        this.perDayAddPlacesLimit = perDayAddPlacesLimit;
        this.perDayAddPostsLimit = perDayAddPostsLimit;
        this.perPlaceImagesLimit = perPlaceImagesLimit;
        this.perPostImagesLimit = perPostImagesLimit;
        this.perPlaceProductsLimit = perPlaceProductsLimit;
        this.perDayAddPlacesLimitEnabled = perDayAddPlacesLimitEnabled;
        this.perDayAddPostsLimitEnabled = perDayAddPostsLimitEnabled;
        this.perPlaceImagesLimitEnabled = perPlaceImagesLimitEnabled;
        this.perPostImagesLimitEnabled = perPostImagesLimitEnabled;

        this.perProductDefaultImageLimitEnabled = perProductDefaultImageLimitEnabled;
        this.perProductMaxImagesLimitEnabled = perProductMaxImagesLimitEnabled;
        this.perProductDefaultImagesLimit = perProductDefaultImagesLimit;
        this.perProductMaxImagesLimit = perProductMaxImagesLimit;
    }

    public boolean getPerProductDefaultImageLimitEnabled() {
        return perProductDefaultImageLimitEnabled;
    }

    public void setPerProductDefaultImageLimitEnabled(boolean perProductDefaultImageLimitEnabled) {
        this.perProductDefaultImageLimitEnabled = perProductDefaultImageLimitEnabled;
    }

    public long getPerProductDefaultImagesLimit() {
        return perProductDefaultImagesLimit;
    }

    public void setPerProductDefaultImagesLimit(long perProductDefaultImagesLimit) {
        this.perProductDefaultImagesLimit = perProductDefaultImagesLimit;
    }

    public long getPerProductMaxImagesLimit() {
        return perProductMaxImagesLimit;
    }

    public void setPerProductMaxImagesLimit(long perProductMaxImagesLimit) {
        this.perProductMaxImagesLimit = perProductMaxImagesLimit;
    }

    public void setPerProductMaxImagesLimit(boolean perProductMaxImagesLimit) {
        perProductMaxImagesLimitEnabled = perProductMaxImagesLimit;
    }

    public boolean getPerProductMaxImagesLimitEnabled() {
        return perProductMaxImagesLimitEnabled;
    }

    public void setPerProductMaxImagesLimitEnabled(boolean perProductMaxImagesLimitEnabled) {
        this.perProductMaxImagesLimitEnabled = perProductMaxImagesLimitEnabled;
    }

    public void setPerProductMaxImagesLimitEnabled(long perProductMaxImagesLimitEnabled) {
        this.perProductMaxImagesLimit = perProductMaxImagesLimitEnabled;
    }

    public long getTotalPlacesLimit() {
        return totalPlacesLimit;
    }

    public void setTotalPlacesLimit(long totalPlacesLimit) {
        this.totalPlacesLimit = totalPlacesLimit;
    }

    public long getPerDayAddPlacesLimit() {
        return perDayAddPlacesLimit;
    }

    public void setPerDayAddPlacesLimit(long perDayAddPlacesLimit) {
        this.perDayAddPlacesLimit = perDayAddPlacesLimit;
    }

    public long getPerDayAddPostsLimit() {
        return perDayAddPostsLimit;
    }

    public void setPerDayAddPostsLimit(long perDayAddPostsLimit) {
        this.perDayAddPostsLimit = perDayAddPostsLimit;
    }

    public long getPerPlaceImagesLimit() {
        return perPlaceImagesLimit;
    }

    public void setPerPlaceImagesLimit(long perPlaceImagesLimit) {
        this.perPlaceImagesLimit = perPlaceImagesLimit;
    }

    public long getPerPostImagesLimit() {
        return perPostImagesLimit;
    }

    public void setPerPostImagesLimit(long perPostImagesLimit) {
        this.perPostImagesLimit = perPostImagesLimit;
    }

    public long getPerPlaceProductsLimit() {
        return perPlaceProductsLimit;
    }

    public void setPerPlaceProductsLimit(long perPlaceProductsLimit) {
        this.perPlaceProductsLimit = perPlaceProductsLimit;
    }

    public boolean getPerDayAddPlacesLimitEnabled() {
        return perDayAddPlacesLimitEnabled;
    }

    public void setPerDayAddPlacesLimitEnabled(boolean perDayAddPlacesLimitEnabled) {
        this.perDayAddPlacesLimitEnabled = perDayAddPlacesLimitEnabled;
    }

    public boolean getPerDayAddPostsLimitEnabled() {
        return perDayAddPostsLimitEnabled;
    }

    public void setPerDayAddPostsLimitEnabled(boolean perDayAddPostsLimitEnabled) {
        this.perDayAddPostsLimitEnabled = perDayAddPostsLimitEnabled;
    }

    public boolean getPerPlaceImagesLimitEnabled() {
        return perPlaceImagesLimitEnabled;
    }

    public void setPerPlaceImagesLimitEnabled(boolean perPlaceImagesLimitEnabled) {
        this.perPlaceImagesLimitEnabled = perPlaceImagesLimitEnabled;
    }

    public boolean getPerPostImagesLimitEnabled() {
        return perPostImagesLimitEnabled;
    }

    public void setPerPostImagesLimitEnabled(boolean perPostImagesLimitEnabled) {
        this.perPostImagesLimitEnabled = perPostImagesLimitEnabled;
    }
}

