package com.example.Non-Block;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeExchange()
                .pathMatchers("/auth/**").permitAll()  // Auth endpoints accessibles sans authentification
                .anyExchange().authenticated() // Toutes les autres requêtes nécessitent une authentification
            .and()
            .addFilterAt(new JwtAuthFilter(), SecurityWebFiltersOrder.AUTHENTICATION); // Filtrage JWT

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> User.builder()
                .username(username)
                .password("{noop}password")  // Le password peut être géré selon les besoins
                .roles("USER")
                .build();
    }
}

