package com.ayprojects.helpinghands.models;

public class Address {
    private String thoroughfare;
    private String subthoroughfare;
    private String feature_name;
    private String locality;
    private String admin_area;
    private String sub_admin_area;
    private String country_name;
    private int postal_code;
    private double lat;
    private double lng;
    private String full_address;
    private String landmark;

    public String getThoroughfare() {
        return thoroughfare;
    }

    public void setThoroughfare(String thoroughfare) {
        this.thoroughfare = thoroughfare;
    }

    public String getSubthoroughfare() {
        return subthoroughfare;
    }

    public void setSubthoroughfare(String subthoroughfare) {
        this.subthoroughfare = subthoroughfare;
    }

    public String getFeature_name() {
        return feature_name;
    }

    public void setFeature_name(String feature_name) {
        this.feature_name = feature_name;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAdmin_area() {
        return admin_area;
    }

    public void setAdmin_area(String admin_area) {
        this.admin_area = admin_area;
    }

    public String getSub_admin_area() {
        return sub_admin_area;
    }

    public void setSub_admin_area(String sub_admin_area) {
        this.sub_admin_area = sub_admin_area;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }
}
