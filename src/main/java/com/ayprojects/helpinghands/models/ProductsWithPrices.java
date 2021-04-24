package com.ayprojects.helpinghands.models;

import java.util.List;

public class ProductsWithPrices extends NameAndTranslationsObj {
    private String productPrice;
    private String selectedUnit;
    private String userEnteredProductName;
    private String color;
    private String size;
    private String productDesc;
    private String productId;
    private List<String> likeIds;
    private String unitQty;
    private List<String> images;

    public ProductsWithPrices(String productPrice, String selectedUnit, String userEnteredProductName, String color, String size, String productDesc, String productId, List<String> likeIds) {
        this.productPrice = productPrice;
        this.selectedUnit = selectedUnit;
        this.userEnteredProductName = userEnteredProductName;
        this.color = color;
        this.size = size;
        this.productDesc = productDesc;
        this.productId = productId;
        this.likeIds = likeIds;
    }

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

    public ProductsWithPrices(String enteredProductPrice, String enteredProductUnit, String enteredProductName, String enteredProductColor, String enteredProductSize, String enteredProductDesc) {
        this.productPrice = enteredProductPrice;
        this.selectedUnit = enteredProductUnit;
        this.userEnteredProductName = enteredProductName;
        this.color = enteredProductColor;
        this.size = enteredProductSize;
        this.productDesc = enteredProductDesc;
    }

    public String getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(String unitQty) {
        this.unitQty = unitQty;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getLikeIds() {
        return likeIds;
    }

    public void setLikeIds(List<String> likeIds) {
        this.likeIds = likeIds;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
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