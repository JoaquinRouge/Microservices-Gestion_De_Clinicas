package com.joaquinrouge.clinics.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.joaquinrouge.clinics.user.configuration.filter.JwtAuthenticationFilter;
import com.joaquinrouge.clinics.user.utils.JwtUtils;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtUtils jwtUtils;
	
	public SecurityConfig(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    return http
	        .csrf(csrf -> csrf.disable())
	        .addFilterBefore(new JwtAuthenticationFilter(jwtUtils),
	        		BasicAuthenticationFilter.class)
	        .build();
	}

    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}

