package com.joaquinrouge.clinics.auth.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.joaquinrouge.clinics.auth.dto.UserModel;

@Component
public class JwtUtils {
	
	@Value("${security.jwt.private.key}")
	private String privateKey;
	
	@Value("${security.jwt.user.generator}")
	private String userGenerator;
	
	public String generateToken(Authentication auth) {
		
		Algorithm alg = Algorithm.HMAC256(privateKey);
		
		UserModel user = (UserModel) auth.getPrincipal();
		
		String authorities = auth.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		return JWT.create()
				.withIssuer(userGenerator)
				.withSubject(user.getUsername())
				.withClaim("authorities", authorities)
				.withClaim("id", user.getId())
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + (60 * 60000)))
				.withJWTId(UUID.randomUUID().toString())
				.withNotBefore(new Date(System.currentTimeMillis()))
				.sign(alg);
		
	}
	
	public DecodedJWT validateJWT(String token) {
		
		try {
			
			Algorithm alg = Algorithm.HMAC256(privateKey);
			
			JWTVerifier verifier = JWT.require(alg)
					.withIssuer(userGenerator)
					.build();
			
			return verifier.verify(token);
		}catch(JWTVerificationException e) {
			throw new JWTVerificationException("Error verifying the token");
		}
	}
	
	public String getUsername(DecodedJWT token) {
		return token.getSubject().toString();
	}
	
	public Claim getSpecificClaim(DecodedJWT token,String claim) {
		return token.getClaim(claim);
	}
	
	public Map<String,Claim> getAllClaims(DecodedJWT token){
		return token.getClaims();
	}
	
}
