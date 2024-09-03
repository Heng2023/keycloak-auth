package org.example.keycloakauth.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Service
public class KeycloakService {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakService.class);

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak-admin.username}")
    private String keycloakAdminUsername;

    @Value("${keycloak-admin.password}")
    private String keycloakAdminPassword;

    public Response createUser(String username, String email, String password) {
        Keycloak keycloak = null;
        try {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(keycloakServerUrl)
                    .realm("master") // Use the master realm to authenticate as admin
                    .clientId("admin-cli") // Using admin-cli for admin tasks
                    .username(keycloakAdminUsername)
                    .password(keycloakAdminPassword)
                    .build();

            UserRepresentation user = new UserRepresentation();
            user.setUsername(username);
            user.setEmail(email);
            user.setEnabled(true);

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(password);
            credential.setTemporary(false);

            user.setCredentials(Collections.singletonList(credential));

            Response response = keycloak.realm(keycloakRealm).users().create(user);

            logger.info("Keycloak response status: {}", response.getStatus());
            if (response.getStatus() != 201) {
                logger.error("Failed to create user: {}", response.readEntity(String.class));
            }

            return response;
        } finally {
            if (keycloak != null) {
                keycloak.close();
            }
        }
    }
}
