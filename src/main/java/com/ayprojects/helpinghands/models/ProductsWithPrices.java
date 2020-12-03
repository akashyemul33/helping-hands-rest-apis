package com.ayprojects.helpinghands.models;

public class ProductsWithPrices {
    private String productPrice;
    private String selectedUnit;
    private ProductName productName;

    public ProductsWithPrices(String price, String unit, String productNameStr) {
        this.productPrice = price;
        this.selectedUnit = unit;
        this.userEnteredProductName = productNameStr;
    }

    public String getUserEnteredProductName() {
        return userEnteredProductName;
    }

    public void setUserEnteredProductName(String userEnteredProductName) {
        this.userEnteredProductName = userEnteredProductName;
    }

    private String userEnteredProductName;
    private String productId;

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(String selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public ProductName getProductName() {
        return productName;
    }

    public void setProductName(ProductName productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ProductsWithPrices(String productPrice, String selectedUnit, ProductName productName, String userEnteredProductName, String productId) {
        this.productPrice = productPrice;
        this.selectedUnit = selectedUnit;
        this.productName = productName;
        this.userEnteredProductName = userEnteredProductName;
        this.productId = productId;
    }
}