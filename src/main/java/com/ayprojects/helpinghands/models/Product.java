package com.ayprojects.helpinghands.models;

public class Product extends CommonUsedAttributes{
    private long product_id;
    private EmbededImage product_image;
    private String default_unit;
    private long place_sub_category_id;
    private ProductNames product_names;
    private double avg_price;

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }


    public String getDefault_unit() {
        return default_unit;
    }

    public void setDefault_unit(String default_unit) {
        this.default_unit = default_unit;
    }

    public EmbededImage getProduct_image() {
        return product_image;
    }

    public void setProduct_image(EmbededImage product_image) {
        this.product_image = product_image;
    }

    public long getPlace_sub_category_id() {
        return place_sub_category_id;
    }

    public void setPlace_sub_category_id(long place_sub_category_id) {
        this.place_sub_category_id = place_sub_category_id;
    }

    public ProductNames getProduct_names() {
        return product_names;
    }

    public void setProduct_names(ProductNames product_names) {
        this.product_names = product_names;
    }

    public double getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(double avg_price) {
        this.avg_price = avg_price;
    }

    public Product(String schemaVersion,String createdDateTime,String modifiedDateTime,String status,long product_id, EmbededImage product_image, String default_unit, long place_sub_category_id, ProductNames product_names, double avg_price) {
        this.product_id = product_id;
        this.product_image = product_image;
        this.default_unit = default_unit;
        this.place_sub_category_id = place_sub_category_id;
        this.product_names = product_names;
        this.avg_price = avg_price;
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
    }
}

class ProductNames {
    private String productname_in_english;
    private String productname_in_marathi;
    private String productname_in_hindi;

    public String getProductname_in_english() {
        return productname_in_english;
    }

    public void setProductname_in_english(String productname_in_english) {
        this.productname_in_english = productname_in_english;
    }

    public String getProductname_in_marathi() {
        return productname_in_marathi;
    }

    public void setProductname_in_marathi(String productname_in_marathi) {
        this.productname_in_marathi = productname_in_marathi;
    }

    public String getProductname_in_hindi() {
        return productname_in_hindi;
    }

    public void setProductname_in_hindi(String productname_in_hindi) {
        this.productname_in_hindi = productname_in_hindi;
    }


}