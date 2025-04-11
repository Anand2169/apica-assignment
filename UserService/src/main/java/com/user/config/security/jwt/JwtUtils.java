package com.user.config.security.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils implements JwtService{
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${user-service.app.jwtSecret}")
	private String jwtSecret;

	@Value("${user-service.app.jwtAccessExpirationMs}")
	private Integer jwtAccessExpirationMs;	
	
	@Value("${user-service.app.jwtRefreshExpirationMs}")
	private Integer jwtRefreshExpirationMs;
	
	@Override
	public String getJwtTokenFromHeader(String bearerToken) {		
		logger.debug("Authorization Header: {}", bearerToken);
		return (bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
	}

	@Override
	public String generateAccessTokenFromUsername(UserDetails userDetails) {
		return Jwts.builder()
				   .subject(userDetails.getUsername())
				   .issuedAt(new Date())
				   .expiration(new Date((new Date()).getTime() + jwtAccessExpirationMs))
				   .signWith(key())
				   .compact();
	}
	
	@Override
	public String generateRefreshToken(UserDetails userDetails) {
	    return Jwts.builder()
	            .subject(userDetails.getUsername())
	            .issuedAt(new Date())
	            .expiration(new Date((new Date()).getTime() + jwtRefreshExpirationMs))
	            .signWith(key())
	            .compact();
	}
	
	@Override
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
	}		

	@Override
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
			return true;
		 } catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		} catch (JwtException e) {
			logger.error("JWT token error: {}", e.getMessage());
		}

		return false;
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}	
}