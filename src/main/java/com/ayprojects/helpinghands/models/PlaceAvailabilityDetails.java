package com.ayprojects.helpinghands.models;

public class PlaceAvailabilityDetails {
    private boolean provide24into7;
    private String placeOpeningTime;
    private String placeClosingTime;
    private String lunchStartTime;
    private String lunchEndTime;
    private boolean sun;
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;

    public PlaceAvailabilityDetails() {

    }

    public PlaceAvailabilityDetails(boolean sun, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat) {
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
    }

    public PlaceAvailabilityDetails(boolean provide24into7, String placeOpeningTime, String placeClosingTime, String lunchStartTime, String lunchEndTime, boolean sun, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat) {
        this.provide24into7 = provide24into7;
        this.placeOpeningTime = placeOpeningTime;
        this.placeClosingTime = placeClosingTime;
        this.lunchStartTime = lunchStartTime;
        this.lunchEndTime = lunchEndTime;
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
    }

    public boolean isProvide24into7() {
        return provide24into7;
    }

    public void setProvide24into7(boolean provide24into7) {
        this.provide24into7 = provide24into7;
    }

    public String getPlaceOpeningTime() {
        return placeOpeningTime;
    }

    public void setPlaceOpeningTime(String placeOpeningTime) {
        this.placeOpeningTime = placeOpeningTime;
    }

    public String getPlaceClosingTime() {
        return placeClosingTime;
    }

    public void setPlaceClosingTime(String placeClosingTime) {
        this.placeClosingTime = placeClosingTime;
    }

    public String getLunchStartTime() {
        return lunchStartTime;
    }

    public void setLunchStartTime(String lunchStartTime) {
        this.lunchStartTime = lunchStartTime;
    }

    public String getLunchEndTime() {
        return lunchEndTime;
    }

    public void setLunchEndTime(String lunchEndTime) {
        this.lunchEndTime = lunchEndTime;
    }

    public boolean isSun() {
        return sun;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }

    public boolean isMon() {
        return mon;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public boolean isTue() {
        return tue;
    }

    public void setTue(boolean tue) {
        this.tue = tue;
    }

    public boolean isWed() {
        return wed;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public boolean isThu() {
        return thu;
    }

    public void setThu(boolean thu) {
        this.thu = thu;
    }

    public boolean isFri() {
        return fri;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public boolean isSat() {
        return sat;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }
}