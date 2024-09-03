package org.example.keycloakauth.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "spring-app")
public class Controller {

    @GetMapping("/greeting")
    public String greeting(){
        return "Hello World From keyCloak";
    }
}
