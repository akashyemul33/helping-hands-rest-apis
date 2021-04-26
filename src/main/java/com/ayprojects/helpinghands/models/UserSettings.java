package com.ayprojects.helpinghands.models;

public class UserSettings {
    private long totalPlacesLimit;
    private long perDayAddPlacesLimit;
    private long perDayAddPostsLimit;
    private long perPlaceImagesLimit;
    private long perPostImagesLimit;
    private boolean isPerDayAddPlacesLimitEnabled;
    private boolean isPerDayAddPostsLimitEnabled;
    private boolean isPerPlaceImagesLimitEnabled;
    private boolean isPerPostImagesLimitEnabled;
    private boolean isPerProductDefaultImageLimitEnabled;
    private long perProductDefaultImagesLimit;
    private long perProductMaxImagesLimit;
    private boolean isPerProductMaxImagesLimitEnabled;
    private long perPlaceProductsLimit;

    public UserSettings(long totalPlacesLimit, long perDayAddPlacesLimit, long perDayAddPostsLimit, long perPlaceImagesLimit, long perPostImagesLimit, long perPlaceProductsLimit, boolean isPerDayAddPlacesLimitEnabled, boolean isPerDayAddPostsLimitEnabled, boolean isPerPlaceImagesLimitEnabled, boolean isPerPostImagesLimitEnabled, long perProductDefaultImagesLimit, boolean isPerProductDefaultImageLimitEnabled, boolean isPerProductMaxImagesLimitEnabled, long perProductMaxImagesLimit) {
        this.totalPlacesLimit = totalPlacesLimit;
        this.perDayAddPlacesLimit = perDayAddPlacesLimit;
        this.perDayAddPostsLimit = perDayAddPostsLimit;
        this.perPlaceImagesLimit = perPlaceImagesLimit;
        this.perPostImagesLimit = perPostImagesLimit;
        this.perPlaceProductsLimit = perPlaceProductsLimit;
        this.isPerDayAddPlacesLimitEnabled = isPerDayAddPlacesLimitEnabled;
        this.isPerDayAddPostsLimitEnabled = isPerDayAddPostsLimitEnabled;
        this.isPerPlaceImagesLimitEnabled = isPerPlaceImagesLimitEnabled;
        this.isPerPostImagesLimitEnabled = isPerPostImagesLimitEnabled;

        this.isPerProductDefaultImageLimitEnabled = isPerProductDefaultImageLimitEnabled;
        this.isPerProductMaxImagesLimitEnabled = isPerProductMaxImagesLimitEnabled;
        this.perProductDefaultImagesLimit = perProductDefaultImagesLimit;
        this.perProductMaxImagesLimit = perProductMaxImagesLimit;
    }

    public boolean isPerProductDefaultImageLimitEnabled() {
        return isPerProductDefaultImageLimitEnabled;
    }

    public void setPerProductDefaultImageLimitEnabled(boolean perProductDefaultImageLimitEnabled) {
        isPerProductDefaultImageLimitEnabled = perProductDefaultImageLimitEnabled;
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
        isPerProductMaxImagesLimitEnabled = perProductMaxImagesLimit;
    }

    public boolean isPerProductMaxImagesLimitEnabled() {
        return isPerProductMaxImagesLimitEnabled;
    }

    public void setPerProductMaxImagesLimitEnabled(boolean perProductMaxImagesLimitEnabled) {
        isPerProductMaxImagesLimitEnabled = perProductMaxImagesLimitEnabled;
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

    public boolean isPerDayAddPlacesLimitEnabled() {
        return isPerDayAddPlacesLimitEnabled;
    }

    public void setPerDayAddPlacesLimitEnabled(boolean perDayAddPlacesLimitEnabled) {
        isPerDayAddPlacesLimitEnabled = perDayAddPlacesLimitEnabled;
    }

    public boolean isPerDayAddPostsLimitEnabled() {
        return isPerDayAddPostsLimitEnabled;
    }

    public void setPerDayAddPostsLimitEnabled(boolean perDayAddPostsLimitEnabled) {
        isPerDayAddPostsLimitEnabled = perDayAddPostsLimitEnabled;
    }

    public boolean isPerPlaceImagesLimitEnabled() {
        return isPerPlaceImagesLimitEnabled;
    }

    public void setPerPlaceImagesLimitEnabled(boolean perPlaceImagesLimitEnabled) {
        isPerPlaceImagesLimitEnabled = perPlaceImagesLimitEnabled;
    }

    public boolean isPerPostImagesLimitEnabled() {
        return isPerPostImagesLimitEnabled;
    }

    public void setPerPostImagesLimitEnabled(boolean perPostImagesLimitEnabled) {
        isPerPostImagesLimitEnabled = perPostImagesLimitEnabled;
    }
}
