package com.ayprojects.helpinghands.models;

import java.util.List;

public class ProductsWithPrices extends NameAndTranslationsObj {
    private String productPrice;
    private String selectedUnit;
    private String userEnteredProductName;
    private List<String> colors;
    private List<String> sizes;
    private List<String> otherAttributes;
    private String productDesc;
    private String productId;
    private List<String> likeIds;
    private String unitQty;
    private List<String> imgUrlsLow;
    private List<String> imgUrlsHigh;

    public ProductsWithPrices(String productPrice, String selectedUnit, String userEnteredProductName, List<String> colors, List<String> sizes, String productDesc, String productId, List<String> likeIds) {
        this.productPrice = productPrice;
        this.selectedUnit = selectedUnit;
        this.userEnteredProductName = userEnteredProductName;
        this.colors = colors;
        this.sizes = sizes;
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

    public ProductsWithPrices(String enteredProductPrice, String enteredProductUnit, String enteredProductName, List<String> enteredProductColors, List<String> enteredProductSizes, String enteredProductDesc) {
        this.productPrice = enteredProductPrice;
        this.selectedUnit = enteredProductUnit;
        this.userEnteredProductName = enteredProductName;
        this.colors = enteredProductColors;
        this.sizes = enteredProductSizes;
        this.productDesc = enteredProductDesc;
    }

    public List<String> getOtherAttributes() {
        return otherAttributes;
    }

    public void setOtherAttributes(List<String> otherAttributes) {
        this.otherAttributes = otherAttributes;
    }

    public List<String> getImgUrlsHigh() {
        return imgUrlsHigh;
    }

    public void setImgUrlsHigh(List<String> imgUrlsHigh) {
        this.imgUrlsHigh = imgUrlsHigh;
    }

    public String getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(String unitQty) {
        this.unitQty = unitQty;
    }

    public List<String> getImgUrlsLow() {
        return imgUrlsLow;
    }

    public void setImgUrlsLow(List<String> imgUrlsLow) {
        this.imgUrlsLow = imgUrlsLow;
    }

    public List<String> getLikeIds() {
        return likeIds;
    }

    public void setLikeIds(List<String> likeIds) {
        this.likeIds = likeIds;
    }


    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
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