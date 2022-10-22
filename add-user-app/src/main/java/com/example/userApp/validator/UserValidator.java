package com.example.userApp.validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.userApp.model.User;
/**
 * Validator class used when creating a User
 * @author idris
 *
 */
public class UserValidator implements Validator {
	private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

	@Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
	/**
	 * Method that validate User (form)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		if(!validFormatUsername(user.getUsername()))errors.rejectValue("username", "username.format","Username cannot contain special character ! ");
		if(!frenchUser(user.getCountry()))errors.rejectValue("country", "country.not.France","Only French people can be registered ! ");
		if(!matchPhoneNumber(user.getPhoneNumber())) errors.rejectValue("phoneNumber", "phone.number.format.invalid","Phone number format is invalid try like 01 23 45 67 89 ! ");
		if(!isValidDate(user.getBirthdate())) errors.rejectValue("birthdate", "birthdate.format.incorrect","Date format must be dd/MM/yyyy ! ");
		else {
			if(!major(user.getBirthdate())) errors.rejectValue("birthdate", "birthdate.not.major","Only major people can be registered ! ");
		}
		if(!isGender(user.getGender())) errors.rejectValue("gender", "gender.incorrect","Correct genders are Male or Female ! ");
	}
	
	private boolean validFormatUsername(String username) {
		if(username!=null) return username.matches("[a-zA-Z0-9_]*");
		return true;
	}
	/**
	 * Date inputs format matches dd/MM/yyyy
	 * @param birthday
	 * @return true if birthday matches good date format, false otherwise
	 */
	private boolean isValidDate(String birthday) {
		if(birthday==null) return false;
		try {
			
			LocalDate.parse(birthday,this.formatter);
		} catch(DateTimeParseException e) {
			return false;
		}
		return true;
	}
	/**
	 * Phonenumber inputs matches specific format
	 * @param phoneNumber
	 * @return true if no phonenumber or phonenumber matches specific formats
	 */
	private boolean matchPhoneNumber(String phoneNumber) {
		if(phoneNumber!=null && phoneNumber.length()>20) return false;
		if(phoneNumber==null || "".equals(phoneNumber) || phoneNumber.replaceAll("\\s", "").equals("")) { 
			return true;
		}
		return phoneNumber.matches("\\d{10}") 
				|| phoneNumber.matches("\\d{2}[-\\.\\s]\\d{2}[-\\.\\s]\\d{2}[-\\.\\s]\\d{2}") 
				|| phoneNumber.matches("\\+\\d[1-4][-\\.\\s]\\d{9,10}") 
				|| phoneNumber.matches("\\+\\d[11-15]");
	}
	/**
	 * User created is an adult
	 * @param date birthdate of user
	 * @return true if adult (age>=18)
	 */
	private boolean major(String date) {
		if(date==null) return false;
		LocalDate currentDate = LocalDate.now();
		LocalDate birthday = LocalDate.parse(date, this.formatter);
		Period age = Period.between(birthday,currentDate);
		return age.getYears()>=18;
	}
	/**
	 * Users country of residence is France
	 * @param country country of residence
	 * @return true if country is "France" ignoring case
	 */
	private boolean frenchUser(String country) {
		return "France".equalsIgnoreCase(country);
	}
	/**
	 * Users gender is valid
	 * @param gender users gender
	 * @return true if "male" or "female" ignoring the case
	 */
	private boolean isGender(String gender) {
		if(gender!=null && gender.length()>6) return false;
		return gender==null || "".equalsIgnoreCase(gender.trim()) ||
		gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female");
	}
}
