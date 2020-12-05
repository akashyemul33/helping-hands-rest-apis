package com.ayprojects.helpinghands.models;

public class ProductsWithPrices {
    private String productPrice;
    private String selectedUnit;
    private String userEnteredProductName;
    private String productId;

    public ProductsWithPrices(String price, String unit, String productNameStr) {
        this.productPrice = price;
        this.selectedUnit = unit;
        this.userEnteredProductName = productNameStr;
    }

    public ProductsWithPrices(String productPrice, String selectedUnit, String userEnteredProductName, String productId) {
        this.productPrice = productPrice;
        this.selectedUnit = selectedUnit;
        this.userEnteredProductName = userEnteredProductName;
        this.productId = productId;
    }
    public ProductsWithPrices() {
    }

    public String getUserEnteredProductName() {
        return userEnteredProductName;
    }

    public void setUserEnteredProductName(String userEnteredProductName) {
        this.userEnteredProductName = userEnteredProductName;
    }

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}