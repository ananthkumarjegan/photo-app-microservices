package com.javan.photoapp.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javan.photoapp.users.entity.LoginRequestModel;
import com.javan.photoapp.users.entity.UserResponseModel;
import com.javan.photoapp.users.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RefreshScope
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UserService usersService;
	private Environment environment;
	
	public AuthenticationFilter(UserService usersService, 
			Environment environment, 
			AuthenticationManager authenticationManager) {
		this.usersService = usersService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginRequestModel loginDetails = new ObjectMapper().readValue(request.getInputStream(),
					LoginRequestModel.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					loginDetails.getEmail(), loginDetails.getPassword(), new ArrayList<>()));
		} catch (Exception e) {
			throw new BadCredentialsException(
					messages.getMessage("AuthenticationFilter.badCredentials", "Bad credentials"));
			
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String userName = ((User) authResult.getPrincipal()).getUsername();
		UserResponseModel userDetails = usersService.getUserByEmail(userName);
		System.out.println(environment.getProperty("token.secret") + "---" + environment.getProperty("token.expiration_time"));
		String jwtToken = Jwts.builder().setSubject(userDetails.getEmail())
				.setExpiration(
						new Date(System.currentTimeMillis() + new Long(environment.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS256, environment.getProperty("token.secret")).compact();

		response.addHeader("token", jwtToken);
		response.addHeader("userName", userName);
	} 
    
	
}
