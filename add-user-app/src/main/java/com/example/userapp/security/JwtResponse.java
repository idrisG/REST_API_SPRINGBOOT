package com.example.userapp.security;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

	private static String type = "Bearer";
	private String token;
	private String username;
	private List<String> roles;
	
	public JwtResponse(String token, String username, List<String> roles) {
		this.token = token;
		this.username = username;
		this.roles = roles;
	}
	
}
