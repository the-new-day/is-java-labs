package org.dealership.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dealership.security.keycloak")
public class KeycloakProperties {
    private String serverUrl;
    private String realm;
    private String managerRealmRole = "MANAGER";
    private String adminClientId;
    private String adminClientSecret;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getManagerRealmRole() {
        return managerRealmRole;
    }

    public void setManagerRealmRole(String managerRealmRole) {
        this.managerRealmRole = managerRealmRole;
    }

    public String getAdminClientId() {
        return adminClientId;
    }

    public void setAdminClientId(String adminClientId) {
        this.adminClientId = adminClientId;
    }

    public String getAdminClientSecret() {
        return adminClientSecret;
    }

    public void setAdminClientSecret(String adminClientSecret) {
        this.adminClientSecret = adminClientSecret;
    }
}
