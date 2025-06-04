package com.joaquinrouge.clinics.user.configuration.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.joaquinrouge.clinics.user.utils.JwtUtils;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)throws ServletException, IOException {
		
		String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(jwt != null) {
			
			jwt = jwt.substring(7);
		
			try {
				DecodedJWT decodedToken = jwtUtils.validateJWT(jwt);
				String auths = jwtUtils.getSpecificClaim(decodedToken, "authorities").asString();
				String username = jwtUtils.getUsername(decodedToken);
				Collection<? extends GrantedAuthority> authsList = 
						AuthorityUtils.commaSeparatedStringToAuthorityList(auths);
				
				SecurityContext context = SecurityContextHolder.getContext();
				
				Authentication auth = new UsernamePasswordAuthenticationToken(username,null,authsList);
				
				context.setAuthentication(auth);
				
				SecurityContextHolder.setContext(context);
				
			}catch(JWTVerificationException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{ \"error\": \"Invalid or expired token\" }");
                return;
			}
		}
		
		filterChain.doFilter(request, response);
		
	}
}

