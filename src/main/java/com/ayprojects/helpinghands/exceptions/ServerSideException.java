package com.ayprojects.helpinghands.exceptions;


public class ServerSideException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -1407565127992155708L;
    private Long resourceId;

    public ServerSideException(String message){
        super(message);
    }

    public ServerSideException(Long resourceId, String message) {
        super(message);
        this.setResourceId(resourceId);
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}
