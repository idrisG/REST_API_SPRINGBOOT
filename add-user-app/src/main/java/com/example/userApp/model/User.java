package com.example.userApp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Database entity stored in the table users
 * @author idris
 *
 */
@Entity
@Table(name = "users")
@Data
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Username cannot be blank ! ")
	@Size(max=40, message = "Username cannot have more than 40 character ! ")
	private String username;
	
	@NotBlank(message = "Birthdate cannot be blank ! ")
    private String birthdate;

	@NotBlank(message = "Country of residence cannot be blank ! ")
	private String country;

	private String phoneNumber;  
	
	private String gender;  

	public User() {
		
	}
	public User(String username, String birthdate, String country, String phoneNumber, String gender) {
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
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public String toString() {
		String s =" User { Username : "+getUsername()+
				" ; Birthdate :  "+getBirthdate()+
				" ; Country of residence : "+getCountry()+
				" ; Phone Number : "+getPhoneNumber()+
				" ; Gender : "+getGender()+" }";
		return s;  
	
	}
	
}