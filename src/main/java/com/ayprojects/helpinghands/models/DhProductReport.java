package com.ayprojects.helpinghands.models;

public class DhProductReport extends AllCommonUsedAttributes{
    private String productReportTypeId;
    private String productReportTypeTitle;

    public String getProductReportDescription() {
        return productReportDescription;
    }

    public void setProductReportDescription(String productReportDescription) {
        this.productReportDescription = productReportDescription;
    }

    private String productReportDescription;
    private String productName;
    private String productId;
    private String productCategory;
    private String addedBy;

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getProductReportTypeId() {
        return productReportTypeId;
    }

    public void setProductReportTypeId(String productReportTypeId) {
        this.productReportTypeId = productReportTypeId;
    }

    public String getProductReportTypeTitle() {
        return productReportTypeTitle;
    }

    public void setProductReportTypeTitle(String productReportTypeTitle) {
        this.productReportTypeTitle = productReportTypeTitle;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}
