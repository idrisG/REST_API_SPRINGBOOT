package com.example.userapp.auth;

import java.util.Date;

import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

	@Value("${app.jwtSecret}")
	private String jwtSecret;
	@Value("${app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	public String generateJwtToken(Authentication authentication) {
		UserDetails employeePrincipal = (UserDetails) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(employeePrincipal.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+this.jwtExpirationMs))
				.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSecret)))
				.compact();
	}
	//TODO Deal with Exceptions
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch(SignatureException e) {
			
		} catch(MalformedJwtException e) {
			
		} catch(ExpiredJwtException e) {
			
		} catch(UnsupportedJwtException e) {
			
		} catch(IllegalArgumentException e) {
			
		}
		return false;
	}

	public String getUsernamFromJwtToken(String jwt) {
		return Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
	}

}
