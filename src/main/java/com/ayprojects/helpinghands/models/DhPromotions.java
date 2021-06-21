package com.ayprojects.helpinghands.models;

import org.springframework.data.annotation.Transient;

import java.util.List;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class DhPromotions extends AllCommonUsedAttributes {
    private String promotionId;
    private String promotionType;
    private String placeId;
    private String addedBy;
    private String promotionTitle;
    private String promotionDesc;
    private List<String> videoThumbnails;
    private List<String> videoUrlsLow;
    @Transient
    @Ignore
    private String offerMsg;
    private List<String> dhCommentsIds;
    private boolean postCommentsOnOff;
    @Transient
    @Ignore
    private List<DhComments> dhComments;
    private List<String> videoUrlsHigh;
    private List<String> promotionImagesLow;
    private List<String> promotionImagesHigh;
    private Contact contactDetails;
    private String fullAddress;
    private String offerStartTime;
    private String offerEndTime;
    private String offerStartDate;
    private String offerEndDate;
    private long numberOfLikes;
    private List<String> likedUserIds;

    public long getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(long numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public List<String> getLikedUserIds() {
        return likedUserIds;
    }

    public void setLikedUserIds(List<String> likedUserIds) {
        this.likedUserIds = likedUserIds;
    }

    public String getOfferStartDate() {
        return offerStartDate;
    }

    public void setOfferStartDate(String offerStartDate) {
        this.offerStartDate = offerStartDate;
    }

    public String getOfferEndDate() {
        return offerEndDate;
    }

    public void setOfferEndDate(String offerEndDate) {
        this.offerEndDate = offerEndDate;
    }

    private boolean areDetailsSameAsRegistered;
    private long numberOfViews;
    private long numberOfComments;
    @Transient
    @Ignore
    private String userName;//username
    @Transient
    @Ignore
    private String userImage;//userImage
    private String placeName;
    private String placeCategory;
    private List<String> viewIds;
    private List<DhViews> topViews;

    public DhPromotions() {

    }

    public boolean isPostCommentsOnOff() {
        return postCommentsOnOff;
    }

    public void setPostCommentsOnOff(boolean postCommentsOnOff) {
        this.postCommentsOnOff = postCommentsOnOff;
    }

    public List<String> getDhCommentsIds() {
        return dhCommentsIds;
    }

    public void setDhCommentsIds(List<String> dhCommentsIds) {
        this.dhCommentsIds = dhCommentsIds;
    }

    public List<DhComments> getDhComments() {
        return dhComments;
    }

    public void setDhComments(List<DhComments> dhComments) {
        this.dhComments = dhComments;
    }

    public String getOfferMsg() {
        return offerMsg;
    }

    public void setOfferMsg(String offerMsg) {
        this.offerMsg = offerMsg;
    }

    public List<String> getVideoThumbnails() {
        return videoThumbnails;
    }

    public void setVideoThumbnails(List<String> videoThumbnails) {
        this.videoThumbnails = videoThumbnails;
    }

    public List<String> getVideoUrlsLow() {
        return videoUrlsLow;
    }

    public void setVideoUrlsLow(List<String> videoUrlsLow) {
        this.videoUrlsLow = videoUrlsLow;
    }

    public List<String> getVideoUrlsHigh() {
        return videoUrlsHigh;
    }

    public void setVideoUrlsHigh(List<String> videoUrlsHigh) {
        this.videoUrlsHigh = videoUrlsHigh;
    }

    public List<String> getPromotionImagesHigh() {
        return promotionImagesHigh;
    }

    public void setPromotionImagesHigh(List<String> promotionImagesHigh) {
        this.promotionImagesHigh = promotionImagesHigh;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceCategory() {
        return placeCategory;
    }

    public void setPlaceCategory(String placeCategory) {
        this.placeCategory = placeCategory;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
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

    public String getPromotionTitle() {
        return promotionTitle;
    }

    public void setPromotionTitle(String promotionTitle) {
        this.promotionTitle = promotionTitle;
    }

    public String getPromotionDesc() {
        return promotionDesc;
    }

    public void setPromotionDesc(String promotionDesc) {
        this.promotionDesc = promotionDesc;
    }

    public List<String> getPromotionImagesLow() {
        return promotionImagesLow;
    }

    public void setPromotionImagesLow(List<String> promotionImagesLow) {
        this.promotionImagesLow = promotionImagesLow;
    }

    public Contact getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(Contact contactDetails) {
        this.contactDetails = contactDetails;
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

    public long getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(long numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public List<String> getViewIds() {
        return viewIds;
    }

    public void setViewIds(List<String> viewIds) {
        this.viewIds = viewIds;
    }

    public List<DhViews> getTopViews() {
        return topViews;
    }

    public void setTopViews(List<DhViews> topViews) {
        this.topViews = topViews;
    }

    public long getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(long numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

}

