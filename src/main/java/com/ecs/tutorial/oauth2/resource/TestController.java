package com.ecs.tutorial.oauth2.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

    @GetMapping("/normal")
    public String normalEndpoint(Principal principal) {
        return "test with " + principal;
    }

    @GetMapping("/admin")
    public String adminEndpoint(Principal principal) {
        return "test with " + principal;
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        return "public";
    }

}
