package com.ayprojects.helpinghands.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DhTicket extends AllCommonUsedAttributes {

    @JsonProperty("ticketId")
    private String ticketId;

    @JsonProperty("issueTitle")
    private String issueTitle;

    @JsonProperty("issueDesc")
    private String issueDesc;

    @JsonProperty("issueType")
    private String issueType;

    @JsonProperty("issueRaisedBy")
    private String issueRaisedBy;

    @JsonProperty("isIssueResolved")
    private Boolean isIssueResolved;

    public Boolean getIssueResolved() {
        return isIssueResolved;
    }

    public void setIssueResolved(Boolean issueResolved) {
        isIssueResolved = issueResolved;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueDesc() {
        return issueDesc;
    }

    public void setIssueDesc(String issueDesc) {
        this.issueDesc = issueDesc;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getIssueRaisedBy() {
        return issueRaisedBy;
    }

    public void setIssueRaisedBy(String issueRaisedBy) {
        this.issueRaisedBy = issueRaisedBy;
    }

    public Boolean getIsIssueResolved() {
        return isIssueResolved;
    }

    public void setIsIssueResolved(Boolean isIssueResolved) {
        this.isIssueResolved = isIssueResolved;
    }

}
