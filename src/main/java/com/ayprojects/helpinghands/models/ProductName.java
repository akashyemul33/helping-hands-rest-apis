package com.ayprojects.helpinghands.models;

class ProductName {
    private String productnameInEnglish;
    private String productnameInMarathi;
    private String productnameInHindi;

    public ProductName(String productnameInEnglish, String productnameInMarathi, String productnameInHindi) {
        this.productnameInEnglish = productnameInEnglish;
        this.productnameInMarathi = productnameInMarathi;
        this.productnameInHindi = productnameInHindi;
    }

    public String getProductnameInEnglish() {
        return productnameInEnglish;
    }

    public void setProductnameInEnglish(String productnameInEnglish) {
        this.productnameInEnglish = productnameInEnglish;
    }

    public String getProductnameInMarathi() {
        return productnameInMarathi;
    }

    public void setProductnameInMarathi(String productnameInMarathi) {
        this.productnameInMarathi = productnameInMarathi;
    }

    public String getProductnameInHindi() {
        return productnameInHindi;
    }

    public void setProductnameInHindi(String productnameInHindi) {
        this.productnameInHindi = productnameInHindi;
    }
}