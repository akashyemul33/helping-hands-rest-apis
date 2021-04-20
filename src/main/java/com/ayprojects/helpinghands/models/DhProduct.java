package com.ayprojects.helpinghands.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class DhProduct extends AllCommonUsedAttributes {
    public String defaultName;
    public List<LangValueObj> translations;
    private String productId;
    private String defaultUnit;
    private String mainPlaceCategoryId;
    private List<String> subPlaceCategoryIds;
    private String categoryName;
    private List<String> subCategoryNames;
    private String addedBy;
    private String avgPrice;
    private String unitQty;
    private String imgUrlLow;
    private String imgUrlHigh;

    public DhProduct() {

    }

    public DhProduct(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String productId, String defaultUnit, String mainPlaceCategoryId, List<String> subPlaceCategoryIds, String categoryName, String addedBy, String defaultName, List<LangValueObj> translations, String avgPrice) {
        this.schemaVersion = schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.status = status;
        this.productId = productId;
        this.defaultUnit = defaultUnit;
        this.mainPlaceCategoryId = mainPlaceCategoryId;
        this.subPlaceCategoryIds = subPlaceCategoryIds;
        this.categoryName = categoryName;
        this.addedBy = addedBy;
        this.defaultName = defaultName;
        this.translations = translations;
        this.avgPrice = avgPrice;
    }

    public DhProduct(String defaultUnit, String defaultName, String avgPrice) {
        this.defaultUnit = defaultUnit;
        this.defaultName = defaultName;
        this.avgPrice = avgPrice;
    }

    public List<String> getSubCategoryNames() {
        return subCategoryNames;
    }

    public void setSubCategoryNames(List<String> subCategoryNames) {
        this.subCategoryNames = subCategoryNames;
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

    public String getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(String unitQty) {
        this.unitQty = unitQty;
    }

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

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public List<String> getSubPlaceCategoryIds() {
        return subPlaceCategoryIds;
    }

    public void setSubPlaceCategoryIds(List<String> subPlaceCategoryIds) {
        this.subPlaceCategoryIds = subPlaceCategoryIds;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(String defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    public String getMainPlaceCategoryId() {
        return mainPlaceCategoryId;
    }

    public void setMainPlaceCategoryId(String mainPlaceCategoryId) {
        this.mainPlaceCategoryId = mainPlaceCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

}

