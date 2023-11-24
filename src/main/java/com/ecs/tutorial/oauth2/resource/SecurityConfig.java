package com.ecs.tutorial.oauth2.resource;

import net.minidev.json.JSONArray;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/public/**").permitAll() // Allow public access to /public endpoints
                .anyRequest().authenticated() // Require authentication for all other requests
                .and()
                .oauth2Login()
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userAuthoritiesMapper(userAuthoritiesMapper()))

                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll();
        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            Optional<OidcUserAuthority> awsAuthority = (Optional<OidcUserAuthority>) authorities.stream()
                    .filter(grantedAuthority -> "OIDC_USER".equals(grantedAuthority.getAuthority()))
                    .findFirst();

            if (awsAuthority.isPresent()) {
                List<String> cognitoGroups = (List<String>) awsAuthority.get().getAttributes().get("cognito:groups");

                if (cognitoGroups != null) {
                    return cognitoGroups.stream()
                            .map(group -> new SimpleGrantedAuthority("ROLE_" + group))
                            .collect(Collectors.toSet());
                }
            }

            return mappedAuthorities;

        };
    }
}

