package com.ayprojects.helpinghands.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class DhProduct extends AllCommonUsedAttributes {
    private String productId;
    private String productImageId;
    private String productImagePath;
    private String defaultUnit;

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

    private String mainPlaceCategoryId;
    private String subPlaceCategoryId;
    private String categoryName;
    private String addedBy;
    private String defaultName;
    private List<LangValueObj> translations;
    private String avgPrice;

    public DhProduct() {

    }

    /*public DhProduct(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String productId, String productImageId, String productImagePath, String defaultUnit, String mainPlaceCategoryId, String subPlaceCategoryId, String categoryName, double avgPrice, String addedBy) {
        this.schemaVersion = schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.status = status;
        this.productId = productId;
        this.productImageId = productImageId;
        this.productImagePath = productImagePath;
        this.defaultUnit = defaultUnit;
        this.mainPlaceCategoryId = mainPlaceCategoryId;
        this.categoryName = categoryName;
        this.avgPrice = avgPrice;
        this.subPlaceCategoryId = subPlaceCategoryId;
        this.addedBy = addedBy;
    }*/

    public DhProduct(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String productId, String productImageId, String productImagePath, String defaultUnit, String mainPlaceCategoryId, String subPlaceCategoryId, String categoryName, String addedBy, String defaultName, List<LangValueObj> translations, String avgPrice) {
        this.schemaVersion = schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.status = status;
        this.productId = productId;
        this.productImageId = productImageId;
        this.productImagePath = productImagePath;
        this.defaultUnit = defaultUnit;
        this.mainPlaceCategoryId = mainPlaceCategoryId;
        this.subPlaceCategoryId = subPlaceCategoryId;
        this.categoryName = categoryName;
        this.addedBy = addedBy;
        this.defaultName = defaultName;
        this.translations = translations;
        this.avgPrice = avgPrice;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getSubPlaceCategoryId() {
        return subPlaceCategoryId;
    }

    public void setSubPlaceCategoryId(String subPlaceCategoryId) {
        this.subPlaceCategoryId = subPlaceCategoryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(String productImageId) {
        this.productImageId = productImageId;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
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

