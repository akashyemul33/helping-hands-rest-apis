package com.ayprojects.helpinghands.models;

public class PlaceAvailabilityDetails {
    private boolean provide24into7;
    private String placeOpeningTime;
    private String placeClosingTime;
    private boolean haveNoLunchHours;
    private String lunchStartTime;
    private String lunchEndTime;
    private boolean haveExchangeFacility;

    public boolean isAnyTimeExchange() {
        return anyTimeExchange;
    }

    public void setAnyTimeExchange(boolean anyTimeExchange) {
        this.anyTimeExchange = anyTimeExchange;
    }

    private boolean anyTimeExchange;
    private String exchangeStartTime;
    private String exchangeEndTime;

    public boolean getHaveNoLunchHours() {
        return haveNoLunchHours;
    }

    public void setHaveNoLunchHours(boolean haveNoLunchHours) {
        this.haveNoLunchHours = haveNoLunchHours;
    }

    public boolean isHaveExchangeFacility() {
        return haveExchangeFacility;
    }

    public void setHaveExchangeFacility(boolean haveExchangeFacility) {
        this.haveExchangeFacility = haveExchangeFacility;
    }

    public String getExchangeStartTime() {
        return exchangeStartTime;
    }

    public void setExchangeStartTime(String exchangeStartTime) {
        this.exchangeStartTime = exchangeStartTime;
    }

    public String getExchangeEndTime() {
        return exchangeEndTime;
    }

    public void setExchangeEndTime(String exchangeEndTime) {
        this.exchangeEndTime = exchangeEndTime;
    }

    public PlaceAvailabilityDetails() {

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

    private boolean sun;
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;

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

    public PlaceAvailabilityDetails(boolean sun, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat) {
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
    }

}