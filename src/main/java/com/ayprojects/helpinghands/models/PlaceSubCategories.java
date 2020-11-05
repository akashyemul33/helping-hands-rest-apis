package com.ayprojects.helpinghands.models;

public class PlaceSubCategories extends CommonUsedAttributes{
    private long place_sub_category_id;
    private EmbededImage place_sub_category_image;

    public long getPlace_sub_category_id() {
        return place_sub_category_id;
    }

    public void setPlace_sub_category_id(long place_sub_category_id) {
        this.place_sub_category_id = place_sub_category_id;
    }

    public EmbededImage getPlace_sub_category_image() {
        return place_sub_category_image;
    }

    public void setPlace_sub_category_image(EmbededImage place_sub_category_image) {
        this.place_sub_category_image = place_sub_category_image;
    }

    public PlaceSubCategories(long place_sub_category_id, EmbededImage place_sub_category_image) {
        this.place_sub_category_id = place_sub_category_id;
        this.place_sub_category_image = place_sub_category_image;
    }
}

class PlaceSubCategoryName{
    private String placesubcategoryname_in_english;
    private String placesubcategoryname_in_marathi;
    private String placesubcategoryname_in_hindi;

    public String getPlacesubcategoryname_in_english() {
        return placesubcategoryname_in_english;
    }

    public void setPlacesubcategoryname_in_english(String placesubcategoryname_in_english) {
        this.placesubcategoryname_in_english = placesubcategoryname_in_english;
    }

    public String getPlacesubcategoryname_in_marathi() {
        return placesubcategoryname_in_marathi;
    }

    public void setPlacesubcategoryname_in_marathi(String placesubcategoryname_in_marathi) {
        this.placesubcategoryname_in_marathi = placesubcategoryname_in_marathi;
    }

    public String getPlacesubcategoryname_in_hindi() {
        return placesubcategoryname_in_hindi;
    }

    public void setPlacesubcategoryname_in_hindi(String placesubcategoryname_in_hindi) {
        this.placesubcategoryname_in_hindi = placesubcategoryname_in_hindi;
    }
}
