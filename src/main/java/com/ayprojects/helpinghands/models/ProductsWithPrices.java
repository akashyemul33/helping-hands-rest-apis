package com.ayprojects.helpinghands.models;

public class ProductsWithPrices {
    private double productPrice;
    private String selectedUnit;
    private ProductName productName;
    private String productId;

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
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

    public ProductsWithPrices(double productPrice, String selectedUnit, ProductName productName, String productId) {
        this.productPrice = productPrice;
        this.selectedUnit = selectedUnit;
        this.productName = productName;
        this.productId = productId;
    }
}