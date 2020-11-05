package com.ayprojects.helpinghands.models;

import java.util.List;

public class Posts extends CommonUsedAttributes{

    private long post_id;
    private String post_type;
    private long place_id;
    private long added_by;
    private String post_title;
    private String post_desc;
    private List<EmbededImage> post_images;
    private Contact contact_details;
    private Address address_details;
    private String offer_start_time;
    private String offer_end_time;
    private boolean are_details_same_as_registered;
    private long number_of_ratings;
    private long number_of_views;
    private double avg_rating;
    private List<Long> ratings;
    private List<Long> views;

    public long getPost_id() {
        return post_id;
    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public long getPlace_id() {
        return place_id;
    }

    public void setPlace_id(long place_id) {
        this.place_id = place_id;
    }

    public long getAdded_by() {
        return added_by;
    }

    public void setAdded_by(long added_by) {
        this.added_by = added_by;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_desc() {
        return post_desc;
    }

    public void setPost_desc(String post_desc) {
        this.post_desc = post_desc;
    }

    public List<EmbededImage> getPost_images() {
        return post_images;
    }

    public void setPost_images(List<EmbededImage> post_images) {
        this.post_images = post_images;
    }

    public Contact getContact_details() {
        return contact_details;
    }

    public void setContact_details(Contact contact_details) {
        this.contact_details = contact_details;
    }

    public Address getAddress_details() {
        return address_details;
    }

    public void setAddress_details(Address address_details) {
        this.address_details = address_details;
    }

    public String getOffer_start_time() {
        return offer_start_time;
    }

    public void setOffer_start_time(String offer_start_time) {
        this.offer_start_time = offer_start_time;
    }

    public String getOffer_end_time() {
        return offer_end_time;
    }

    public void setOffer_end_time(String offer_end_time) {
        this.offer_end_time = offer_end_time;
    }

    public boolean isAre_details_same_as_registered() {
        return are_details_same_as_registered;
    }

    public void setAre_details_same_as_registered(boolean are_details_same_as_registered) {
        this.are_details_same_as_registered = are_details_same_as_registered;
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
}

