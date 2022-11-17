package com.example.userapp.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Employee DTO
 * @author idris
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
	@NotBlank(message = "username cannot be blank ! ")
	public String username;
	public String password;
	public String role = "USER";
	public String toString() {
		return getUsername() + " : " + getRole();
	}
}
