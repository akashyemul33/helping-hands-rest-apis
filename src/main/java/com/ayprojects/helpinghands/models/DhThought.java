package com.ayprojects.helpinghands.models;

public class DhThought extends AllCommonUsedAttributes{
    private String imgUrlLow;
    private String imgUrlHigh;
    private String thoughtId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;
    private long itemId;

    public String getImgUrlLow() {
        return imgUrlLow;
    }

    public void setImgUrlLow(String imgUrlLow) {
        this.imgUrlLow = imgUrlLow;
    }

    public String getImgUrlHigh() {
        return imgUrlHigh;
    }

    public void setImgUrlHigh(String imgUrlHigh) {
        this.imgUrlHigh = imgUrlHigh;
    }

    public String getThoughtId() {
        return thoughtId;
    }

    public void setThoughtId(String thoughtId) {
        this.thoughtId = thoughtId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
