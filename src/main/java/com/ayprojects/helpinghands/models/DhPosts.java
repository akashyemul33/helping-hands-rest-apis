package com.ayprojects.helpinghands.models;

import java.util.List;

public class DhPosts extends AllCommonUsedAttributes {
    private String postId;
    private String postType;
    private String placeId;
    private String addedBy;
    private String postTitle;
    private String postDesc;
    private List<EmbededImage> postImages;
    private Contact contactDetails;
    private Address addressDetails;
    private String offerStartTime;
    private String offerEndTime;
    private boolean areDetailsSameAsRegistered;
    private long numberOfRatings;
    private long numberOfViews;
    private double avgRating;
    private List<String> ratingIds;
    private List<String> viewIds;
    private List<DhRating_comments> topRatings;
    private List<DhViews> topViews;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public List<EmbededImage> getPostImages() {
        return postImages;
    }

    public void setPostImages(List<EmbededImage> postImages) {
        this.postImages = postImages;
    }

    public Contact getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(Contact contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Address getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(Address addressDetails) {
        this.addressDetails = addressDetails;
    }

    public String getOfferStartTime() {
        return offerStartTime;
    }

    public void setOfferStartTime(String offerStartTime) {
        this.offerStartTime = offerStartTime;
    }

    public String getOfferEndTime() {
        return offerEndTime;
    }

    public void setOfferEndTime(String offerEndTime) {
        this.offerEndTime = offerEndTime;
    }

    public boolean isAreDetailsSameAsRegistered() {
        return areDetailsSameAsRegistered;
    }

    public void setAreDetailsSameAsRegistered(boolean areDetailsSameAsRegistered) {
        this.areDetailsSameAsRegistered = areDetailsSameAsRegistered;
    }

    public long getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(long numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public long getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(long numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public List<String> getRatingIds() {
        return ratingIds;
    }

    public void setRatingIds(List<String> ratingIds) {
        this.ratingIds = ratingIds;
    }

    public List<String> getViewIds() {
        return viewIds;
    }

    public void setViewIds(List<String> viewIds) {
        this.viewIds = viewIds;
    }

    public List<DhRating_comments> getTopRatings() {
        return topRatings;
    }

    public void setTopRatings(List<DhRating_comments> topRatings) {
        this.topRatings = topRatings;
    }

    public List<DhViews> getTopViews() {
        return topViews;
    }

    public void setTopViews(List<DhViews> topViews) {
        this.topViews = topViews;
    }

    public DhPosts(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String postId, String postType, String placeId, String addedBy, String postTitle, String postDesc, List<EmbededImage> postImages, Contact contactDetails, Address addressDetails, String offerStartTime, String offerEndTime, boolean areDetailsSameAsRegistered, long numberOfRatings, long numberOfViews, double avgRating, List<String> ratingIds, List<String> viewIds, List<DhRating_comments> topRatings, List<DhViews> topViews) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.postId = postId;
        this.postType = postType;
        this.placeId = placeId;
        this.addedBy = addedBy;
        this.postTitle = postTitle;
        this.postDesc = postDesc;
        this.postImages = postImages;
        this.contactDetails = contactDetails;
        this.addressDetails = addressDetails;
        this.offerStartTime = offerStartTime;
        this.offerEndTime = offerEndTime;
        this.areDetailsSameAsRegistered = areDetailsSameAsRegistered;
        this.numberOfRatings = numberOfRatings;
        this.numberOfViews = numberOfViews;
        this.avgRating = avgRating;
        this.ratingIds = ratingIds;
        this.viewIds = viewIds;
        this.topRatings = topRatings;
        this.topViews = topViews;
    }
}

