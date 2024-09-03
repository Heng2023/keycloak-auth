package org.example.keycloakauth;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SecurityScheme(
        name = "spring-app",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                clientCredentials = @OAuthFlow(
                        tokenUrl = "http://localhost:8080/realms/spring-keycloak/protocol/openid-connect/token"
                ),
                password = @OAuthFlow(
                        tokenUrl = "http://localhost:8080/realms/spring-keycloak/protocol/openid-connect/token"
                )
        )
)
@SpringBootApplication
public class KeycloakAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeycloakAuthApplication.class, args);
    }

}
