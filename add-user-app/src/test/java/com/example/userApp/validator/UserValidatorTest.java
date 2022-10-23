package com.example.userApp.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.example.userApp.model.User;

/**
 * Unit test class for UserValidator
 * 
 * We don't test the model specific validation since we already test it in UserRepositoryTest class
 * @author idris
 *
 */
public class UserValidatorTest {
	
	private UserValidator userValidator = new UserValidator(); 
	private User user;
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
	 * Asserts that there is 5 errors since our 5 fields have errors
	 */
	@Test
	public void testValidation_failureMajorFullError() {
	    user = getMajorUserFullError();
	    errors = new BeanPropertyBindingResult(user, "user");
	    userValidator.validate(user, errors);
	    assertThat(errors.getErrorCount()).isEqualTo(5);
	}

	/**
	 * Test failure of validation in case minor user not conform.
	 * Asserts that there is 5 errors since our 5 fields have errors (date format right but user not an adult)
	 */
	@Test
	public void testValidation_failureMinorFullError() {
	    user = getMinorUserFullError();
	    errors = new BeanPropertyBindingResult(user, "user");
	    userValidator.validate(user, errors);
	    assertThat(errors.getErrorCount()).isEqualTo(5);
	}
	
	/**
	 * Create valid user
	 * @return
	 */
	User getUser() {
		return new User("huber","01/01/1996","France","0102030405","male");
	}
	/**
	 * Create invalid Adult user with every field invalid
	 * @return
	 */
	User getMajorUserFullError() {
		return new User("huber!!!","0!!!1/01/1996","France!!!!","0102030405!!!!!","male!!!!!");
	}
	/**
	 * Create invalid Minor user with every field invalid
	 * @return
	 */
	User getMinorUserFullError() {
		return new User("huber!!!","01/01/2008","France!!!!","0102030405!!!!!","male!!!!!");
	}
}
