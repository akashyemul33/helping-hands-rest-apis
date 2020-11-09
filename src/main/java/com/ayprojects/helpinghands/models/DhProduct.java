package com.ayprojects.helpinghands.models;

public class DhProduct extends AllCommonUsedAttributes {
    private String productId;
    private String productImageId;
    private String productImagePath;
    private String defaultUnit;
    private String categoryId;
    private String categoryName;
    private ProductName productName;
    private double avgPrice;

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public DhProduct(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String productId, String productImageId, String productImagePath, String defaultUnit, String categoryId, String categoryName, ProductName productName, double avgPrice) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.productId = productId;
        this.productImageId = productImageId;
        this.productImagePath = productImagePath;
        this.defaultUnit = defaultUnit;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productName = productName;
        this.avgPrice = avgPrice;
    }
}

