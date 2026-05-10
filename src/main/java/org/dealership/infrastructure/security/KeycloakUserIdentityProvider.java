package org.dealership.infrastructure.security;

import jakarta.ws.rs.core.Response;
import org.dealership.application.port.out.security.UserIdentityProvider;
import org.dealership.domain.model.id.UserId;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class KeycloakUserIdentityProvider implements UserIdentityProvider {

    private final KeycloakProperties properties;
    private final Keycloak keycloak;

    public KeycloakUserIdentityProvider(KeycloakProperties properties) {
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
    public UserId createUser(String username, String password, String fullName) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setFirstName(fullName);
        user.setEnabled(true);
        user.setCredentials(List.of(credential));

        try (Response response = keycloak.realm(properties.getRealm()).users().create(user)) {
            String location = response.getLocation().getPath();
            String id = location.replaceAll(".*/", "");
            return new UserId(UUID.fromString(id));
        }
    }
}
