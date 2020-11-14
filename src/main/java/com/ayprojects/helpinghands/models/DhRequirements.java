package com.ayprojects.helpinghands.models;

import java.util.List;

public class DhRequirements extends AllCommonUsedAttributes {
    private String requirementId;
    private String requirement_title;
    private String requirement_desc;
    private String priority;
    private String budget;
    private Contact contact_details;
    private Address address_details;
    private boolean are_details_same_as_registered;
    private long number_of_ratings;
    private long number_of_views;
    private double avg_rating;
    private List<Long> ratings;
    private List<Long> views;

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    public String getRequirement_title() {
        return requirement_title;
    }

    public void setRequirement_title(String requirement_title) {
        this.requirement_title = requirement_title;
    }

    public String getRequirement_desc() {
        return requirement_desc;
    }

    public void setRequirement_desc(String requirement_desc) {
        this.requirement_desc = requirement_desc;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
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

    public DhRequirements(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String requirementId, String requirement_title, String requirement_desc, String priority, String budget, Contact contact_details, Address address_details, boolean are_details_same_as_registered, long number_of_ratings, long number_of_views, double avg_rating, List<Long> ratings, List<Long> views) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.requirementId = requirementId;
        this.requirement_title = requirement_title;
        this.requirement_desc = requirement_desc;
        this.priority = priority;
        this.budget = budget;
        this.contact_details = contact_details;
        this.address_details = address_details;
        this.are_details_same_as_registered = are_details_same_as_registered;
        this.number_of_ratings = number_of_ratings;
        this.number_of_views = number_of_views;
        this.avg_rating = avg_rating;
        this.ratings = ratings;
        this.views = views;
    }
}
