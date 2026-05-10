package org.dealership.infrastructure.security;

import jakarta.ws.rs.NotFoundException;
import org.dealership.application.port.out.security.UserManager;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.UserId;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class KeycloakUserManager implements UserManager {

    private final KeycloakProperties properties;
    private final Keycloak keycloak;

    public KeycloakUserManager(KeycloakProperties properties) {
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

    @Override
    public Optional<UserRecord> findById(UserId userId) {
        try {
            UserRepresentation rep = keycloak.realm(properties.getRealm())
                    .users().get(userId.value().toString()).toRepresentation();
            if (rep == null) return Optional.empty();
            return Optional.of(new UserRecord(UUID.fromString(rep.getId()), resolveFullName(rep)));
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserRecord> listUsers() {
        return keycloak.realm(properties.getRealm()).users().list().stream()
                .map(rep -> new UserRecord(UUID.fromString(rep.getId()), resolveFullName(rep)))
                .toList();
    }

    @Override
    public void updateUser(UserId userId, String fullName) {
        try {
            var resource = keycloak.realm(properties.getRealm()).users().get(userId.value().toString());
            UserRepresentation rep = resource.toRepresentation();
            rep.setFirstName(fullName);
            resource.update(rep);
        } catch (NotFoundException e) {
            throw new EntityNotFoundException("User not found: " + userId);
        }
    }

    @Override
    public void deleteUser(UserId userId) {
        try {
            keycloak.realm(properties.getRealm()).users().get(userId.value().toString()).remove();
        } catch (NotFoundException e) {
            throw new EntityNotFoundException("User not found: " + userId);
        }
    }

    private static String resolveFullName(UserRepresentation rep) {
        String firstName = rep.getFirstName();
        if (firstName != null && !firstName.isBlank()) return firstName;
        String username = rep.getUsername();
        if (username != null && !username.isBlank()) return username;
        return rep.getId();
    }
}
