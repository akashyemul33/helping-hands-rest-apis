package com.ayprojects.helpinghands.models;

import java.util.List;

public class DhRequirements extends AllCommonUsedAttributes {
    private String addedBy;
    private String requirementId;
    private String requirementTitle;
    private String requirementDesc;
    private String priority;
    private String budget;

    public DhRequirements() {

    }

    public String getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(String requirementType) {
        this.requirementType = requirementType;
    }

    private String requirementType;

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public List<String> getRequirementImages() {
        return requirementImages;
    }

    public void setRequirementImages(List<String> requirementImages) {
        this.requirementImages = requirementImages;
    }

    private List<String> requirementImages;
    private Contact contactDetails;
    private Address addressDetails;
    private boolean areDetailsSameAsRegistered;
    private long numberOfRatings;
    private long numberOfViews;
    private double avgRating;
    private List<String> ratingIds;
    private List<String> viewIds;
    private List<DhRatingAndComments> topRatings;
    private List<DhViews> topViews;

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    public String getRequirementTitle() {
        return requirementTitle;
    }

    public void setRequirementTitle(String requirementTitle) {
        this.requirementTitle = requirementTitle;
    }

    public String getRequirementDesc() {
        return requirementDesc;
    }

    public void setRequirementDesc(String requirementDesc) {
        this.requirementDesc = requirementDesc;
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

    public List<DhRatingAndComments> getTopRatings() {
        return topRatings;
    }

    public void setTopRatings(List<DhRatingAndComments> topRatings) {
        this.topRatings = topRatings;
    }

    public List<DhViews> getTopViews() {
        return topViews;
    }

    public void setTopViews(List<DhViews> topViews) {
        this.topViews = topViews;
    }

    public DhRequirements(String schemaVersion, String createdDateTime, String modifiedDateTime, String status,String requirementId, String requirementTitle, String requirementDesc, String priority, String budget, Contact contactDetails, Address addressDetails, boolean areDetailsSameAsRegistered, long numberOfRatings, long numberOfViews, double avgRating, List<String> ratingIds, List<String> viewIds, List<DhRatingAndComments> topRatings, List<DhViews> topViews, List<String> requirementImages,String addedBy,String requirementType) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.requirementId = requirementId;
        this.requirementTitle = requirementTitle;
        this.requirementDesc = requirementDesc;
        this.priority = priority;
        this.budget = budget;
        this.contactDetails = contactDetails;
        this.addressDetails = addressDetails;
        this.areDetailsSameAsRegistered = areDetailsSameAsRegistered;
        this.numberOfRatings = numberOfRatings;
        this.numberOfViews = numberOfViews;
        this.avgRating = avgRating;
        this.ratingIds = ratingIds;
        this.viewIds = viewIds;
        this.topRatings = topRatings;
        this.topViews = topViews;
        this.requirementImages = requirementImages;
        this.addedBy = addedBy;
        this.requirementType = requirementType;
    }
}
