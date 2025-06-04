package com.joaquinrouge.clinics.api_gateway.configuration;

import org.springframework.context.annotation.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable()) // No necesitamos CSRF en API Gateway
            .authorizeExchange(auth -> auth
                .pathMatchers("/eureka/**").permitAll() // permitir acceso a Eureka si es necesario
                .anyExchange().permitAll() // permitir todo lo demás (la validación JWT se hace en los MS)
            )
            .httpBasic(Customizer.withDefaults())
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable);

        return http.build();
    }
}


