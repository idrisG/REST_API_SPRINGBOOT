package com.example.userapp.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Database entity stored in the table users
 * 
 * @author idris
 *
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 8268187804307724173L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 40, message = "Username cannot have more than 40 character ! ")
    @NotBlank(message = "Username cannot be blank ! ")
    private String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birthdate cannot be null ! ")
    private LocalDate birthdate;

    @NotBlank(message = "Country of residence cannot be blank ! ")
    private String country;

    @Size(max = 20, message = "Phone number cannot have more than 20 character ! ")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @JsonFormat
    private Gender gender;

    /**
     * Default constructor
     */
    public User() {

    }

    /**
     * Constructor
     * 
     * @param username
     * @param birthdate
     * @param country
     * @param phoneNumber
     * @param gender
     */
    public User(String username, LocalDate birthdate, String country, String phoneNumber, Gender gender) {
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    /**
     * toString (to display in the console log)
     */
    public String toString() {
        String s = " User { Username : " + getUsername() + " ; Birthdate :  " + getBirthdate()
                + " ; Country of residence : " + getCountry() + " ; Phone Number : " + getPhoneNumber() + " ; Gender : "
                + getGender() + " }";
        return s;

    }

}