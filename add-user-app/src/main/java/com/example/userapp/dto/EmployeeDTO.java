package com.example.userapp.dto;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class EmployeeDTO {
	@Id
	@NotBlank(message = "username cannot be blank ! ")
	public String username;
	public String password;
	public String role = "USER";
	
	public EmployeeDTO() {}
	
	public EmployeeDTO(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String position) {
		this.role = position;
	}
	
	public String toString() {
		return getUsername() + " : " + getRole();
	}
}
