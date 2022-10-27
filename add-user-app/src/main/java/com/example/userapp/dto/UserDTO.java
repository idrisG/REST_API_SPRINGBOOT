package com.example.userapp.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.userapp.model.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;

public class UserDTO {
	
	private Integer id;

	@Size(max=40, message = "Username cannot have more than 40 character ! ")
	@NotBlank(message = "Username cannot be blank ! ")
	private String username;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	@NotNull(message = "Birthdate cannot be null ! ")
    private LocalDate birthdate;
	@NotBlank(message = "Country of residence cannot be blank ! ")
	private String country;

	@Size(max=20, message = "Phone number cannot have more than 20 character ! ")
	private String phoneNumber;  
	
	@Enumerated(EnumType.STRING)
	@JsonFormat
	private Gender gender;  
		
	public UserDTO() {}
	public UserDTO(String username, LocalDate birthdate, String country, String phoneNumber, Gender gender) {
		this.username = username;
		this.birthdate = birthdate;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	} 
}
