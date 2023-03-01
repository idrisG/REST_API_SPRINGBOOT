package com.example.userapp.dto;

import java.time.LocalDate;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.userapp.model.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * User DTO
 * @author idris
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

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
    
}
