package com.ayprojects.helpinghands.models;

import java.util.List;

public class DhStickers extends AllCommonUsedAttributes {
    private String stickerSetId;
    private int stickerCount;
    private String totalPackSize;
    private String stickerPackName;
    private List<Sticker> stickers;

    public String getTotalPackSize() {
        return totalPackSize;
    }

    public void setTotalPackSize(String totalPackSize) {
        this.totalPackSize = totalPackSize;
    }

    public int getStickerCount() {
        return stickerCount;
    }

    public void setStickerCount(int stickerCount) {
        this.stickerCount = stickerCount;
    }

    public String getStickerPackName() {
        return stickerPackName;
    }

    public void setStickerPackName(String stickerPackName) {
        this.stickerPackName = stickerPackName;
    }

    public String getStickerSetId() {
        return stickerSetId;
    }

    public void setStickerSetId(String stickerSetId) {
        this.stickerSetId = stickerSetId;
    }

    public List<Sticker> getStickers() {
        return stickers;
    }

    public void setStickers(List<Sticker> stickers) {
        this.stickers = stickers;
    }


}

