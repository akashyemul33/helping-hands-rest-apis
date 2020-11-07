package com.ayprojects.helpinghands.models;

public class LoginResponse {
    private DhUser dhUser;
    private AccessTokenModel accessToken;

    public DhUser getDhUser() {
        return dhUser;
    }

    public void setDhUser(DhUser dhUser) {
        this.dhUser = dhUser;
    }

    public AccessTokenModel getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessTokenModel accessToken) {
        this.accessToken = accessToken;
    }
}
