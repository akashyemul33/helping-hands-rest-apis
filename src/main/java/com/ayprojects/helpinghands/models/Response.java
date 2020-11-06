package com.ayprojects.helpinghands.models;

import java.util.List;

public class Response<T> {

    private Boolean status;
    private Integer statusCode;
    private String message;
    private Long totalCount;
    private List<T> data;

    public Response() {

    }

    public Response(String message) {
        this.setMessage(message);
    }

    public Response(Boolean status, Integer statusCode, String message, List<T> data) {
        this.setStatus(status);
        this.setStatusCode(statusCode);
        this.setMessage(message);
        this.setData(data);
    }

    public Response(Boolean status, Integer statusCode, String message, List<T> data, Long totalCount) {
        this.setStatus(status);
        this.setStatusCode(statusCode);
        this.setMessage(message);
        this.setData(data);
        this.setTotalCount(totalCount);
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
