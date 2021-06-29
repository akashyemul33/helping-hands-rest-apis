package com.ayprojects.helpinghands.models;

public class UserSettings {

    private long totalPlacesLimit;
    private long perDayAddPlacesLimit;
    private long perDayAddPostsLimit;
    private long perPlaceImagesLimit;
    private long perPostImagesLimit;
    private long perPostMaxImagesLimit;
    private long perPostMaxVideosLimit;
    private long perPostVideosLimit;
    private boolean perDayAddPlacesLimitEnabled;
    private boolean perDayAddPostsLimitEnabled;
    private boolean perPlaceImagesLimitEnabled;
    private boolean perPostImagesLimitEnabled;
    private boolean perPostVideosLimitEnabled;
    private boolean perPostMaxVideosLimitEnabled;
    private boolean promotionVideoTimeLimitEnabled;
    private long promotionVideoTimeLimitInSeconds;

    public boolean isPromotionVideoTimeLimitEnabled() {
        return promotionVideoTimeLimitEnabled;
    }

    public void setPromotionVideoTimeLimitEnabled(boolean promotionVideoTimeLimitEnabled) {
        this.promotionVideoTimeLimitEnabled = promotionVideoTimeLimitEnabled;
    }

    public long getPromotionVideoTimeLimitInSeconds() {
        return promotionVideoTimeLimitInSeconds;
    }

    public void setPromotionVideoTimeLimitInSeconds(long promotionVideoTimeLimitInSeconds) {
        this.promotionVideoTimeLimitInSeconds = promotionVideoTimeLimitInSeconds;
    }

    public long getPerPostMaxImagesLimit() {
        return perPostMaxImagesLimit;
    }

    public void setPerPostMaxImagesLimit(long perPostMaxImagesLimit) {
        this.perPostMaxImagesLimit = perPostMaxImagesLimit;
    }

    public long getPerPostMaxVideosLimit() {
        return perPostMaxVideosLimit;
    }

    public void setPerPostMaxVideosLimit(long perPostMaxVideosLimit) {
        this.perPostMaxVideosLimit = perPostMaxVideosLimit;
    }

    public long getPerPostVideosLimit() {
        return perPostVideosLimit;
    }

    public void setPerPostVideosLimit(long perPostVideosLimit) {
        this.perPostVideosLimit = perPostVideosLimit;
    }

    public boolean isPerPostVideosLimitEnabled() {
        return perPostVideosLimitEnabled;
    }

    public void setPerPostVideosLimitEnabled(boolean perPostVideosLimitEnabled) {
        this.perPostVideosLimitEnabled = perPostVideosLimitEnabled;
    }

    public boolean isPerPostMaxVideosLimitEnabled() {
        return perPostMaxVideosLimitEnabled;
    }

    public void setPerPostMaxVideosLimitEnabled(boolean perPostMaxVideosLimitEnabled) {
        this.perPostMaxVideosLimitEnabled = perPostMaxVideosLimitEnabled;
    }

    public boolean isPerPostMaxImagesLimitEnabled() {
        return perPostMaxImagesLimitEnabled;
    }

    public void setPerPostMaxImagesLimitEnabled(boolean perPostMaxImagesLimitEnabled) {
        this.perPostMaxImagesLimitEnabled = perPostMaxImagesLimitEnabled;
    }

    private boolean perPostMaxImagesLimitEnabled;
    private boolean perProductDefaultImageLimitEnabled;
    private long perProductDefaultImagesLimit;
    private long perProductMaxImagesLimit;
    private boolean perProductMaxImagesLimitEnabled;
    private long perPlaceProductsLimit;
    private boolean notificationsRequired;
    private boolean hhPerPostImgLimitEnabled;
    private long hhPerPostImgLimit;
    private boolean hhShowProfileOnHelp;
    private boolean hhShowProfileOnAddPost;
    private boolean hhPostCommentsOnOff;

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

    public boolean isHhPostCommentsOnOff() {
        return hhPostCommentsOnOff;
    }

    public void setHhPostCommentsOnOff(boolean hhPostCommentsOnOff) {
        this.hhPostCommentsOnOff = hhPostCommentsOnOff;
    }

    public boolean isHhShowProfileOnAddPost() {
        return hhShowProfileOnAddPost;
    }

    public void setHhShowProfileOnAddPost(boolean hhShowProfileOnAddPost) {
        this.hhShowProfileOnAddPost = hhShowProfileOnAddPost;
    }

    public boolean isHhShowProfileOnHelp() {
        return hhShowProfileOnHelp;
    }

    public void setHhShowProfileOnHelp(boolean hhShowProfileOnHelp) {
        this.hhShowProfileOnHelp = hhShowProfileOnHelp;
    }

    public boolean isHhPerPostImgLimitEnabled() {
        return hhPerPostImgLimitEnabled;
    }

    public void setHhPerPostImgLimitEnabled(boolean hhPerPostImgLimitEnabled) {
        this.hhPerPostImgLimitEnabled = hhPerPostImgLimitEnabled;
    }

    public long getHhPerPostImgLimit() {
        return hhPerPostImgLimit;
    }

    public void setHhPerPostImgLimit(long hhPerPostImgLimit) {
        this.hhPerPostImgLimit = hhPerPostImgLimit;
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

    public boolean isPerDayAddPlacesLimitEnabled() {
        return perDayAddPlacesLimitEnabled;
    }

    public void setPerDayAddPlacesLimitEnabled(boolean perDayAddPlacesLimitEnabled) {
        this.perDayAddPlacesLimitEnabled = perDayAddPlacesLimitEnabled;
    }

    public boolean isPerDayAddPostsLimitEnabled() {
        return perDayAddPostsLimitEnabled;
    }

    public void setPerDayAddPostsLimitEnabled(boolean perDayAddPostsLimitEnabled) {
        this.perDayAddPostsLimitEnabled = perDayAddPostsLimitEnabled;
    }

    public boolean isPerPlaceImagesLimitEnabled() {
        return perPlaceImagesLimitEnabled;
    }

    public void setPerPlaceImagesLimitEnabled(boolean perPlaceImagesLimitEnabled) {
        this.perPlaceImagesLimitEnabled = perPlaceImagesLimitEnabled;
    }

    public boolean isPerPostImagesLimitEnabled() {
        return perPostImagesLimitEnabled;
    }

    public void setPerPostImagesLimitEnabled(boolean perPostImagesLimitEnabled) {
        this.perPostImagesLimitEnabled = perPostImagesLimitEnabled;
    }

    public boolean isPerProductDefaultImageLimitEnabled() {
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

    public boolean isPerProductMaxImagesLimitEnabled() {
        return perProductMaxImagesLimitEnabled;
    }

    public void setPerProductMaxImagesLimitEnabled(boolean perProductMaxImagesLimitEnabled) {
        this.perProductMaxImagesLimitEnabled = perProductMaxImagesLimitEnabled;
    }

    public long getPerPlaceProductsLimit() {
        return perPlaceProductsLimit;
    }

    public void setPerPlaceProductsLimit(long perPlaceProductsLimit) {
        this.perPlaceProductsLimit = perPlaceProductsLimit;
    }

    public boolean isNotificationsRequired() {
        return notificationsRequired;
    }

    public void setNotificationsRequired(boolean notificationsRequired) {
        this.notificationsRequired = notificationsRequired;
    }
}

