package com.example.userapp.validator;

import java.time.LocalDate;
import java.time.Period;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.userapp.dto.UserDTO;

/**
 * Validator class used when creating a UserDTO
 * 
 * @author idris
 *
 */
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    /**
     * Method that validate User (form) and stores errors if User invalid
     */
    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        if (!validFormatUsername(userDTO.getUsername())) {
            errors.rejectValue("username", "username.format", "Username cannot contain special character ! ");
        }
        if (!frenchUser(userDTO.getCountry())) {
            errors.rejectValue("country", "country.not.France", "Only French people can be registered ! ");
        }
        if (!matchPhoneNumber(userDTO.getPhoneNumber())) {
            errors.rejectValue("phoneNumber", "phone.number.format.invalid",
                    "Phone number format is invalid try like 01 23 45 67 89 ! ");
        }
        if (!major(userDTO.getBirthdate())) {
            errors.rejectValue("birthdate", "birthdate.not.major", "Only major people can be registered ! ");
        }
    }

    /**
     * Username inputs is alpha-numeric (allowing under score)
     * 
     * @param username
     * @return true if alpha-numeric or null (null already checked in the model)
     */
    private boolean validFormatUsername(String username) {
        if (username != null)
            return username.matches("[a-zA-Z0-9_]*");
        return true;
    }

    /**
     * PhoneNumber inputs matches specific format
     * 
     * @param phoneNumber
     * @return true if no phonenumber or phonenumber matches specific formats
     */
    private boolean matchPhoneNumber(String phoneNumber) {
        return phoneNumber == null || "".equals(phoneNumber) || phoneNumber.trim().equals("")
                || phoneNumber.matches("\\d{10}")
                || phoneNumber.matches("\\d{2}[-\\.\\s]\\d{2}[-\\.\\s]\\d{2}[-\\.\\s]\\d{2}")
                || phoneNumber.matches("\\+\\d[1-4][-\\.\\s]\\d{9,10}") || phoneNumber.matches("\\+\\d[11-15]");
    }

    /**
     * User created is an adult
     * 
     * @param localDate birthdate of user
     * @return true if adult (age>=18)
     */
    private boolean major(LocalDate birthday) {
        if (birthday == null)
            return false;
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthday, currentDate);
        return age.getYears() >= 18;
    }

    /**
     * Users country of residence is France
     * 
     * @param country country of residence
     * @return true if country is "France" ignoring case
     */
    private boolean frenchUser(String country) {
        return "France".equalsIgnoreCase(country);
    }
}
