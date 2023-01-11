package com.hopin.HopIn.security.auth;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.util.TokenUtils;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private TokenUtils tokenUtils;

	private UserDetailsService userDetailsService;
	
	protected final Log LOGGER = LogFactory.getLog(getClass());

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {


		String username;
		
		String authToken = tokenUtils.getToken(request);
		
		try {
	
			if (authToken != null) {
				authToken = authToken.substring(1, authToken.length() - 1);

				username = tokenUtils.getUsernameFromToken(authToken);
				if (username != null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					
					if (tokenUtils.validateToken(authToken, userDetails)) {
						TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
						authentication.setToken(authToken);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
			
		} catch (ExpiredJwtException ex) {
			String requestUrl = request.getRequestURI().toString();
			if (requestUrl.contains("refresh")) {
				System.out.println("TOKEN REFRESH!");
			} else {
				LOGGER.debug("Token expired!");
				throw ex;
			}
		} 
		
		chain.doFilter(request, response);
	}

}