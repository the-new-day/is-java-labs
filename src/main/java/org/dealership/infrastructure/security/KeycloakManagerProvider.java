package org.dealership.infrastructure.security;

import org.dealership.application.port.out.security.ManagerProvider;
import org.dealership.domain.model.id.UserId;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class KeycloakManagerProvider implements ManagerProvider {

    private static final int MAX_MANAGERS = 1000;

    private final KeycloakProperties properties;
    private final Keycloak keycloak;

    public KeycloakManagerProvider(KeycloakProperties properties) {
        this.properties = properties;
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(properties.getServerUrl())
                .realm(properties.getRealm())
                .clientId(properties.getAdminClientId())
                .clientSecret(properties.getAdminClientSecret())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }

    @Override
    public List<UserId> listManagerIds() {
        List<UserRepresentation> members = keycloak.realm(properties.getRealm())
                .roles()
                .get(properties.getManagerRealmRole())
                .getUserMembers(0, MAX_MANAGERS);
        return members.stream()
                .map(UserRepresentation::getId)
                .map(UUID::fromString)
                .map(UserId::new)
                .toList();
    }
}
