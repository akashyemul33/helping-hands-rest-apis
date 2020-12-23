package com.ayprojects.helpinghands.exceptions;

public class ClientSideException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 5867607208798032633L;
    private Long resourceId;

    private String data;

    public ClientSideException(Long resourceId, String message) {
        super(message);
        this.setResourceId(resourceId);
    }

    public ClientSideException(Long resourceId, String data, String message) {
        super(message + "-" + data);
        this.setResourceId(resourceId);
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}
