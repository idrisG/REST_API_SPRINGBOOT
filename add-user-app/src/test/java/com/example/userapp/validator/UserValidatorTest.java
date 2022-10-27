package com.example.userapp.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.example.userapp.dto.UserDTO;
import com.example.userapp.model.Gender;

/**
 * Unit test class for UserValidator
 * 
 * We don't test the model specific validation since we already test it in UserRepositoryTest class
 * @author idris
 *
 */
public class UserValidatorTest {
	
	private UserValidator userValidator = new UserValidator(); 
	private UserDTO user;
	private Errors errors;
	
	/**
	 * Test success of validation in case a user is conform
	 */
	@Test
	public void testValidation_success() {
	    user = getUser();
	    errors = new BeanPropertyBindingResult(user, "user");
	    userValidator.validate(user, errors);
	    assertFalse(errors.hasErrors()); 
	}
	/**
	 * Test failure of validation in case adult user not conform.
	 * Asserts that there is 3 errors since our 3 fields have errors (not birthday or gender)
	 */
	@Test
	public void testValidation_failureMajorFullError() {
	    user = getMajorUserFullError();
	    errors = new BeanPropertyBindingResult(user, "user");
	    userValidator.validate(user, errors);
	    assertThat(errors.getErrorCount()).isEqualTo(3);
	}

	/**
	 * Test failure of validation in case minor user not conform.
	 * Asserts that there is 4 errors since our 4 fields have errors (date format right but user not an adult)
	 */
	@Test
	public void testValidation_failureMinorFullError() {
	    user = getMinorUserFullError();
	    errors = new BeanPropertyBindingResult(user, "user");
	    userValidator.validate(user, errors);
	    assertThat(errors.getErrorCount()).isEqualTo(4);
	}
	
	/**
	 * Create valid user
	 * @return
	 */
	UserDTO getUser() {
		return new UserDTO("huber",LocalDate.of(1996, 01, 01),"France","0102030405",Gender.MALE);
	}
	/**
	 * Create invalid Adult user with every field invalid
	 * @return
	 */
	UserDTO getMajorUserFullError() {
		return new UserDTO("huber!!!",LocalDate.of(1996, 01, 01),"France!!!!","0102030405!!!!!",Gender.MALE);
	}
	/**
	 * Create invalid Minor user with every field invalid
	 * @return
	 */
	UserDTO getMinorUserFullError() {
		return new UserDTO("huber!!!",LocalDate.of(2008, 01, 01),"France!!!!","0102030405!!!!!",null);
	}
}
