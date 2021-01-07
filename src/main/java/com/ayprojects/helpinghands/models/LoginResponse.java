package com.ayprojects.helpinghands.models;

public class LoginResponse extends AllCommonUsedAttributes {
    private DhUser dhUser;
    private DhAppConfig dhAppConfig;

    public DhUser getDhUser() {
        return dhUser;
    }

    public void setDhUser(DhUser dhUser) {
        this.dhUser = dhUser;
    }

    public DhAppConfig getDhAppConfig() {
        return dhAppConfig;
    }

    public void setDhAppConfig(DhAppConfig dhAppConfig) {
        this.dhAppConfig = dhAppConfig;
    }

    public LoginResponse(DhUser dhUser, DhAppConfig dhAppConfig) {
        this.dhUser = dhUser;
        this.dhAppConfig = dhAppConfig;
    }
}
