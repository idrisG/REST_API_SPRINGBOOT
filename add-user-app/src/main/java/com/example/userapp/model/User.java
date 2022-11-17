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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Database entity stored in the table users
 * 
 * @author idris
 *
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
     * toString (to display in the console log)
     */
    public String toString() {
        return new StringBuilder()
        		.append(" User { Username : ").append(this.getUsername())
        		.append(" ; Birthdate :  ").append(this.getBirthdate())
        		.append(" ; Country of residence : ").append(this.getCountry())
        		.append(" ; Phone Number : ").append(this.getPhoneNumber())
        		.append(" ; Gender : ").append(this.getGender()).append(" }").toString();
        		

    }
}