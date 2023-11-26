package com.ecs.tutorial.oauth2.resource;

import net.minidev.json.JSONArray;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
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

import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/public").permitAll()
                                .requestMatchers("/authenticated").authenticated()
                                .requestMatchers("/employee").hasRole("EMPLOYEE")
                                .requestMatchers("/admin").hasRole("ADMIN")
                )
                .oauth2Login(oauth ->
                        oauth.userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userAuthoritiesMapper(userAuthoritiesMapper())
                        )
                );

        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();


            OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) new ArrayList<>(authorities).get(0);
            List<String> cognitoGroups = (List<String>) oidcUserAuthority.getAttributes().get("cognito:groups");

            if (cognitoGroups != null) {
                    return cognitoGroups.stream()
                            .map(group -> new SimpleGrantedAuthority("ROLE_" + group))
                            .collect(Collectors.toSet());
            }

            return mappedAuthorities;

        };
    }

}

