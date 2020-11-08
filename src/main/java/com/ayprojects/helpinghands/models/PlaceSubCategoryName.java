package com.ayprojects.helpinghands.models;

public class PlaceSubCategoryName{
    private String placesubcategorynameInEnglish;
    private String placesubcategorynameInMarathi;
    private String placesubcategorynameInHindi;

    public String getPlacesubcategorynameInEnglish() {
        return placesubcategorynameInEnglish;
    }

    public PlaceSubCategoryName(String placesubcategorynameInEnglish, String placesubcategorynameInMarathi, String placesubcategorynameInHindi) {
        this.placesubcategorynameInEnglish = placesubcategorynameInEnglish;
        this.placesubcategorynameInMarathi = placesubcategorynameInMarathi;
        this.placesubcategorynameInHindi = placesubcategorynameInHindi;
    }

    public void setPlacesubcategorynameInEnglish(String placesubcategorynameInEnglish) {
        this.placesubcategorynameInEnglish = placesubcategorynameInEnglish;
    }

    public String getPlacesubcategorynameInMarathi() {
        return placesubcategorynameInMarathi;
    }

    public void setPlacesubcategorynameInMarathi(String placesubcategorynameInMarathi) {
        this.placesubcategorynameInMarathi = placesubcategorynameInMarathi;
    }

    public String getPlacesubcategorynameInHindi() {
        return placesubcategorynameInHindi;
    }

    public void setPlacesubcategorynameInHindi(String placesubcategorynameInHindi) {
        this.placesubcategorynameInHindi = placesubcategorynameInHindi;
    }
}
