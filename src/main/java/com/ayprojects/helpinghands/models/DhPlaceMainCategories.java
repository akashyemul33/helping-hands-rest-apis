package com.ayprojects.helpinghands.models;

public class DhPlaceMainCategories extends CommonUsedAttributes{
    private long place_category_id;
    private PlaceCategoryName place_category_names;
    private EmbededImage place_category_image;

    public EmbededImage getPlace_category_image() {
        return place_category_image;
    }

    public void setPlace_category_image(EmbededImage place_category_image) {
        this.place_category_image = place_category_image;
    }

    public DhPlaceMainCategories(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, long place_category_id, PlaceCategoryName place_category_names, EmbededImage place_category_image) {
        this.place_category_id = place_category_id;
        this.place_category_names = place_category_names;
        this.place_category_image = place_category_image;
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
    }

    public long getPlace_category_id() {
        return place_category_id;
    }

    public void setPlace_category_id(long place_category_id) {
        this.place_category_id = place_category_id;
    }

    public PlaceCategoryName getPlace_category_names() {
        return place_category_names;
    }

    public void setPlace_category_names(PlaceCategoryName place_category_names) {
        this.place_category_names = place_category_names;
    }

}

class PlaceCategoryName{
    private String placecategoryname_in_english;
    private String placecategoryname_in_marathi;
    private String placecategoryname_in_hindi;

    public String getPlacecategoryname_in_english() {
        return placecategoryname_in_english;
    }

    public void setPlacecategoryname_in_english(String placecategoryname_in_english) {
        this.placecategoryname_in_english = placecategoryname_in_english;
    }

    public String getPlacecategoryname_in_marathi() {
        return placecategoryname_in_marathi;
    }

    public void setPlacecategoryname_in_marathi(String placecategoryname_in_marathi) {
        this.placecategoryname_in_marathi = placecategoryname_in_marathi;
    }

    public String getPlacecategoryname_in_hindi() {
        return placecategoryname_in_hindi;
    }

    public void setPlacecategoryname_in_hindi(String placecategoryname_in_hindi) {
        this.placecategoryname_in_hindi = placecategoryname_in_hindi;
    }
}
