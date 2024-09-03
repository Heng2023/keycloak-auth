package org.example.keycloakauth.controller;

import org.example.keycloakauth.model.UserRequest;
import org.example.keycloakauth.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;

@RestController
public class UserController {

    private final KeycloakService keycloakService;

    public UserController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {
        Response response = keycloakService.createUser(userRequest.getUsername(), userRequest.getEmail(), userRequest.getPassword());

        if (response.getStatus() == 201) {
            return ResponseEntity.status(201).body("User created successfully");
        } else {
            return ResponseEntity.status(response.getStatus()).body("Failed to create user: " + response.getStatusInfo().getReasonPhrase());
        }
    }
}
