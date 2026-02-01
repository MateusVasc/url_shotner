package com.matt.url_shotner.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSec) {
        httpSec
                // REST APIs should be stateless
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // NO HTML form login
                .formLogin(AbstractHttpConfigurer::disable)

                // AUTH rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/users/create",
                                "/users/auth"
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        return httpSec.build();
    }
}
