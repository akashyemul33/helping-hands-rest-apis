package com.ayprojects.helpinghands.models;

public class DhPlace extends AllCommonUsedAttributes {
/*
    private long place_id;
    private String place_type;
    private Address place_address;
    private long place_category_id;
    private String place_category_name;
    private String place_name;
    private String place_desc;
    private Contact place_contact;
    private boolean door_service;
    private List<EmbededImage> place_images;
    private PlaceAvailabilityDays place_avaialable_days;
    private String opening_time;
    private String closing_time;
    private String lunchhour_starttime;
    private String lunchhour_endtime;
    private List<ProductsWithPrice> products_with_price;
    private long number_of_ratings;
    private long number_of_views;
    private double avg_rating;
    private List<Long> ratings;
    private List<Long> views;

    public long getPlace_id() {
        return place_id;
    }

    public void setPlace_id(long place_id) {
        this.place_id = place_id;
    }

    public String getPlace_type() {
        return place_type;
    }

    public void setPlace_type(String place_type) {
        this.place_type = place_type;
    }

    public Address getPlace_address() {
        return place_address;
    }

    public void setPlace_address(Address place_address) {
        this.place_address = place_address;
    }

    public long getPlace_category_id() {
        return place_category_id;
    }

    public void setPlace_category_id(long place_category_id) {
        this.place_category_id = place_category_id;
    }

    public String getPlace_category_name() {
        return place_category_name;
    }

    public void setPlace_category_name(String place_category_name) {
        this.place_category_name = place_category_name;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPlace_desc() {
        return place_desc;
    }

    public void setPlace_desc(String place_desc) {
        this.place_desc = place_desc;
    }

    public Contact getPlace_contact() {
        return place_contact;
    }

    public void setPlace_contact(Contact place_contact) {
        this.place_contact = place_contact;
    }

    public boolean isDoor_service() {
        return door_service;
    }

    public void setDoor_service(boolean door_service) {
        this.door_service = door_service;
    }

    public List<EmbededImage> getPlace_images() {
        return place_images;
    }

    public void setPlace_images(List<EmbededImage> place_images) {
        this.place_images = place_images;
    }

    public PlaceAvailabilityDays getPlace_avaialable_days() {
        return place_avaialable_days;
    }

    public void setPlace_avaialable_days(PlaceAvailabilityDays place_avaialable_days) {
        this.place_avaialable_days = place_avaialable_days;
    }

    public String getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(String opening_time) {
        this.opening_time = opening_time;
    }

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

    public String getLunchhour_starttime() {
        return lunchhour_starttime;
    }

    public void setLunchhour_starttime(String lunchhour_starttime) {
        this.lunchhour_starttime = lunchhour_starttime;
    }

    public String getLunchhour_endtime() {
        return lunchhour_endtime;
    }

    public void setLunchhour_endtime(String lunchhour_endtime) {
        this.lunchhour_endtime = lunchhour_endtime;
    }

    public List<ProductsWithPrice> getProducts_with_price() {
        return products_with_price;
    }

    public void setProducts_with_price(List<ProductsWithPrice> products_with_price) {
        this.products_with_price = products_with_price;
    }

    public long getNumber_of_ratings() {
        return number_of_ratings;
    }

    public void setNumber_of_ratings(long number_of_ratings) {
        this.number_of_ratings = number_of_ratings;
    }

    public long getNumber_of_views() {
        return number_of_views;
    }

    public void setNumber_of_views(long number_of_views) {
        this.number_of_views = number_of_views;
    }

    public double getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(double avg_rating) {
        this.avg_rating = avg_rating;
    }

    public List<Long> getRatings() {
        return ratings;
    }

    public void setRatings(List<Long> ratings) {
        this.ratings = ratings;
    }

    public List<Long> getViews() {
        return views;
    }

    public void setViews(List<Long> views) {
        this.views = views;
    }

    public DhPlace(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, long place_id, String place_type, Address place_address, long place_category_id, String place_category_name, String place_name, String place_desc, Contact place_contact, boolean door_service, List<EmbededImage> place_images, PlaceAvailabilityDays place_avaialable_days, String opening_time, String closing_time, String lunchhour_starttime, String lunchhour_endtime, List<ProductsWithPrice> products_with_price, long number_of_ratings, long number_of_views, double avg_rating, List<Long> ratings, List<Long> views) {
        this.place_id = place_id;
        this.place_type = place_type;
        this.place_address = place_address;
        this.place_category_id = place_category_id;
        this.place_category_name = place_category_name;
        this.place_name = place_name;
        this.place_desc = place_desc;
        this.place_contact = place_contact;
        this.door_service = door_service;
        this.place_images = place_images;
        this.place_avaialable_days = place_avaialable_days;
        this.opening_time = opening_time;
        this.closing_time = closing_time;
        this.lunchhour_starttime = lunchhour_starttime;
        this.lunchhour_endtime = lunchhour_endtime;
        this.products_with_price = products_with_price;
        this.number_of_ratings = number_of_ratings;
        this.number_of_views = number_of_views;
        this.avg_rating = avg_rating;
        this.ratings = ratings;
        this.views = views;
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
    }
}
class ProductsWithPrice{
    private String product_price;
    private String product_unit;
    private ProductNames product_name;
    private long product_id;

    public ProductsWithPrice(long product_id,String product_price, String product_unit, ProductNames product_name) {
        this.product_price = product_price;
        this.product_id = product_id;
        this.product_unit = product_unit;
        this.product_name = product_name;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public ProductNames getProduct_name() {
        return product_name;
    }

    public void setProduct_name(ProductNames product_name) {
        this.product_name = product_name;
    }


}
class PlaceAvailabilityDays{
    private boolean sun;
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;

    public PlaceAvailabilityDays(boolean sun, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat) {
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
    }*/
}

