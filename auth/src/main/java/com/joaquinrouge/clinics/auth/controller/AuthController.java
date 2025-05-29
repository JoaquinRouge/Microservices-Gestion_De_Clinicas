package com.joaquinrouge.clinics.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaquinrouge.clinics.auth.dto.AuthLoginDto;
import com.joaquinrouge.clinics.auth.service.UserDetailsServiceImp;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserDetailsServiceImp userDetails;
	
	public AuthController(UserDetailsServiceImp userDetails) {
		this.userDetails = userDetails;
	}
	
	@PostMapping("/login")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> login(@RequestBody @Valid AuthLoginDto loginData){
		try {
			return ResponseEntity.ok(userDetails.login(loginData));			
		}catch(UsernameNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}catch(BadCredentialsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
