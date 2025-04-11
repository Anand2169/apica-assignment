package com.user.config.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	String getJwtTokenFromHeader(String bearerToken);
	
	String generateAccessTokenFromUsername(UserDetails userDetails);
	String generateRefreshToken(UserDetails userDetails);
	String getUserNameFromJwtToken(String token);
	boolean validateJwtToken(String authToken);	
}