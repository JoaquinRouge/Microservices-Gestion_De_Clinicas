package com.joaquinrouge.clinics.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joaquinrouge.clinics.auth.client.UserClient;
import com.joaquinrouge.clinics.auth.dto.AuthLoginDto;
import com.joaquinrouge.clinics.auth.dto.AuthResponseDto;
import com.joaquinrouge.clinics.auth.dto.UserModel;
import com.joaquinrouge.clinics.auth.utils.JwtUtils;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

	private final UserClient userClient;
	private final JwtUtils	jwtUtils;
	private final PasswordEncoder passwordEncoder;
	
	public UserDetailsServiceImp(UserClient userClient,JwtUtils	jwtUtils,
			PasswordEncoder passwordEncoder) {
		this.userClient = userClient;
		this.jwtUtils = jwtUtils;
		this.passwordEncoder = passwordEncoder;
	};
	
	@Override
	@CircuitBreaker(name = "USER_SERVICE",fallbackMethod = "userFallBackMethod")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserModel user = userClient.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		return user;
	}

	public UserDetails userFallBackMethod(String username,Throwable t) {
		throw new UsernameNotFoundException("User service unavailable. Try again later.");
	}
	
	public AuthResponseDto login(AuthLoginDto data) {
		
		String username = data.username();
		String password = data.password();
		
		Authentication auth = authenticate(username,password);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String jwt = jwtUtils.generateToken(auth);
		
		return new AuthResponseDto(username,"login successful",jwt,true);
		
	}

	public Authentication authenticate(String username, String password) {
		
		UserDetails uDetails = loadUserByUsername(username);
		
		if(uDetails == null) {
			throw new BadCredentialsException("Error during authentication");
		}
		
		if(!passwordEncoder.matches(password, uDetails.getPassword())) {
			throw new BadCredentialsException("Error during authentication");
		}
		
		return new UsernamePasswordAuthenticationToken(uDetails, uDetails.getPassword(),
				uDetails.getAuthorities());
		
	}
	
}
