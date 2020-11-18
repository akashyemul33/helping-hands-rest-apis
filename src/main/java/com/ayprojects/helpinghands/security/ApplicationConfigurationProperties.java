package com.ayprojects.helpinghands.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "appconfig")
public class ApplicationConfigurationProperties {

    private String[] defaultUserRoles;

    private String clientId;

    private String clientSecret;

    private String[] onPopStateUrls;

    private String oauthURI;

    private String svsbaseuri;

    public String getSvsbaseuri() {
        return svsbaseuri;
    }

    public void setSvsbaseuri(String svsbaseuri) {
        this.svsbaseuri = svsbaseuri;
    }

    public String getOauthURI() {
        return oauthURI;
    }

    public void setOauthURI(String oauthURI) {
        this.oauthURI = oauthURI;
    }

    public String[] getDefaultUserRoles() {
        return defaultUserRoles;
    }

    public void setDefaultUserRoles(String[] defaultUserRoles) {
        this.defaultUserRoles = defaultUserRoles;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String[] getOnPopStateUrls() {
        return onPopStateUrls;
    }

    public void setOnPopStateUrls(String[] onPopStateUrls) {
        this.onPopStateUrls = onPopStateUrls;
    }
}
