package com.example.test.keycloak;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class Security {

    private final ConverterToken converterToken;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) {
        try {
            http.csrf().disable().authorizeHttpRequests().anyRequest().authenticated();
            http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(converterToken);
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            return http.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
