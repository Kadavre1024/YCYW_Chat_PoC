package com.ocow.back.payload;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String firstName;
	private String laststName;
	private String password;
	private List<GrantedAuthority> authorities;
	
	
	public JwtResponse(String accessToken, String username, Long id, String firstName, String lastName,String password, List<GrantedAuthority> authorities) {
	  this.token = accessToken;
	  this.username = username;
	  this.id = id;
	  this.firstName = firstName;
	  this.laststName = lastName;
	  this.password = password;
	  this.authorities = authorities;
	}
}
