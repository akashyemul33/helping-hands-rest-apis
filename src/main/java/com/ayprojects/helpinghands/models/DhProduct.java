package com.ayprojects.helpinghands.models;

public class DhProduct extends AllCommonUsedAttributes {
    private String productId;
    private String productImageId;
    private String productImagePath;
    private String defaultUnit;
    private String mainPlaceCategoryId;
    private String subPlaceCategoryId;
    private String categoryName;
    private String addedBy;
    private ProductName productName;
    private double avgPrice;
    private String userEnteredProductName;
    private String langBasedProductName;

    public String getLangBasedProductName() {
        return langBasedProductName;
    }

    public void setLangBasedProductName(String langBasedProductName) {
        this.langBasedProductName = langBasedProductName;
    }

    public String getUserEnteredProductName() {
        return userEnteredProductName;
    }

    public void setUserEnteredProductName(String userEnteredProductName) {
        this.userEnteredProductName = userEnteredProductName;
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

    public ProductName getProductName() {
        return productName;
    }

    public void setProductName(ProductName productName) {
        this.productName = productName;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public DhProduct(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String productId, String productImageId, String productImagePath, String defaultUnit, String mainPlaceCategoryId, String subPlaceCategoryId, String categoryName, ProductName productName, double avgPrice,String addedBy,String userEnteredProductName,String langBasedProductName) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.productId = productId;
        this.productImageId = productImageId;
        this.productImagePath = productImagePath;
        this.defaultUnit = defaultUnit;
        this.mainPlaceCategoryId = mainPlaceCategoryId;
        this.categoryName = categoryName;
        this.productName = productName;
        this.avgPrice = avgPrice;
        this.subPlaceCategoryId = subPlaceCategoryId;
        this.addedBy = addedBy;
        this.userEnteredProductName = userEnteredProductName;
        this.langBasedProductName = langBasedProductName;
    }
}

