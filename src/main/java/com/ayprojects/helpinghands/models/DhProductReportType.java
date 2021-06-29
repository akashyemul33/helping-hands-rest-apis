package com.ayprojects.helpinghands.models;

public class DhProductReportType extends AllCommonUsedAttributes{
    private String typeTitle;
    private String typeDesc;
    private String reportTypeId;

    public String getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(String reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public String getTypeTitle() {
        return typeTitle;
    }
    private String addedBy;

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
}
