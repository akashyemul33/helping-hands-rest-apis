package com.ayprojects.helpinghands.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DhProductForAddingProducts extends AllCommonUsedAttributes {
    public String defaultName;
    private String imgUrlLow;
    private String imgUrlHigh;
    private String nameInMarathi;
    private String nameInHindi;
    private String nameInTelugu;
    private String nameInKannada;
    private String nameInGujarati;
    private String avgPrice;
    private String unitQty;
    private String mainPlaceCategoryId;
    private String subPlaceCategoryIds;
    private String defaultUnit;

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

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

    public String getNameInMarathi() {
        return nameInMarathi;
    }

    public void setNameInMarathi(String nameInMarathi) {
        this.nameInMarathi = nameInMarathi;
    }

    public String getNameInHindi() {
        return nameInHindi;
    }

    public void setNameInHindi(String nameInHindi) {
        this.nameInHindi = nameInHindi;
    }

    public String getNameInTelugu() {
        return nameInTelugu;
    }

    public void setNameInTelugu(String nameInTelugu) {
        this.nameInTelugu = nameInTelugu;
    }

    public String getNameInKannada() {
        return nameInKannada;
    }

    public void setNameInKannada(String nameInKannada) {
        this.nameInKannada = nameInKannada;
    }

    public String getNameInGujarati() {
        return nameInGujarati;
    }

    public void setNameInGujarati(String nameInGujarati) {
        this.nameInGujarati = nameInGujarati;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(String unitQty) {
        this.unitQty = unitQty;
    }

    public String getMainPlaceCategoryId() {
        return mainPlaceCategoryId;
    }

    public void setMainPlaceCategoryId(String mainPlaceCategoryId) {
        this.mainPlaceCategoryId = mainPlaceCategoryId;
    }

    public String getSubPlaceCategoryIds() {
        return subPlaceCategoryIds;
    }

    public void setSubPlaceCategoryIds(String subPlaceCategoryIds) {
        this.subPlaceCategoryIds = subPlaceCategoryIds;
    }

    public String getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(String defaultUnit) {
        this.defaultUnit = defaultUnit;
    }
}

