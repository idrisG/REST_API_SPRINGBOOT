package com.example.userApp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.example.userApp.model.User;
/**
 * Unit test class for repository (entity included)
 * @author idris
 *
 */
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
class UserRepositoryTest {
	
	@Autowired
	UserRepository repository;
	/**
	 * Unit test repository, create user success
	 */
	@Test
	@Rollback(false)
	@Order(1)
	void testCreateUser_success() {
		User user = repository.save(getUser());
		assertThat(user.getId()).isGreaterThan(0);
	}
	/**
	 * Unit test repository, retrieve user success
	 */
	@Test
	@Order(2)
	void testRetireveUser_success() {
		User user = repository.findByUsername("huber");
		assertThat(user.getUsername()).isEqualTo("huber");
	}
	
	/**
	 * Unit test repository, retrieve user failure
	 */
	@Test
	@Order(3)
	void testRetrieveUser_failureWrongUsername() {
		User user = repository.findByUsername("wrong");
		assertThat(user).isNull();
	}
	
	/**
	 * Test to create user with null username
	 * Asserts that it throws ConstraintViolationException
	 */
	@Test
	@Order(4)
	void testCreateUser_failureInvalidNullUsername() {
		Assertions.assertThrows(ConstraintViolationException.class,
				() -> repository.save(getUserNullUsername()));
	}

	/**
	 * Test to create user with null Birthdate
	 * Asserts that it throws ConstraintViolationException
	 */
	@Test
	@Order(5)
	void testCreateUser_failureInvalidNullBirthdate() {
		Assertions.assertThrows(ConstraintViolationException.class,
				() -> repository.save(getUserNullBirthdate()));
	}

	/**
	 * Test to create user with null Country of residence
	 * Asserts that it throws ConstraintViolationException
	 */
	@Test
	@Order(6)
	void testCreateUser_failureInvalidNullCountry() {
		Assertions.assertThrows(ConstraintViolationException.class,
				() -> repository.save(getUserNullCountry()));
	}

	/**
	 * Test to create user with too long phone number
	 * Asserts that it throws ConstraintViolationException
	 */
	@Test
	@Order(7)
	void testCreateUser_failureInvalidTooLongPhoneNumber() {
		Assertions.assertThrows(ConstraintViolationException.class,
				() -> repository.save(getUserTooLongPhoneNumber()));
	}

	/**
	 * Test to create user with too long gender
	 * Asserts that it throws ConstraintViolationException
	 */
	@Test
	@Order(8)
	void testCreateUser_failureInvalidTooLongGender() {
		Assertions.assertThrows(ConstraintViolationException.class,
				() -> repository.save(getUserTooLongGender()));
	}
	
	
	
	
	
	/**
	 * Create valid user
	 * @return
	 */
	User getUser() {
		return new User("huber","01/01/2008","Fra","0102030405","male");
	}
	/**
	 * Create invalid user with username null (Blank)
	 * @return
	 */
	User getUserNullUsername() {
		return new User(null,"01/01/2008","Fra","0102030405","m");
	}
	/**
	 * Create invalid user with birthdate null (Blank)
	 * @return
	 */
	User getUserNullBirthdate() {
		return new User("huber",null,"Fra","0102030405","m");
	}
	/**
	 * Create invalid user with Country of residence null (Blank)
	 * @return
	 */
	User getUserNullCountry() {
		return new User("hubert","01/01/2008",null,"0102030405","m");
	}
	/**
	 * Create invalid user with Phone number longer than 20 character
	 * @return
	 */
	User getUserTooLongPhoneNumber() {
		return new User("hubert","01/01/2008","Fra","123456789 123456789 1","m");
	}
	/**
	 * Create invalid user with Gender longer than 8 character
	 * @return
	 */
	User getUserTooLongGender() {
		return new User("hubert","01/01/2008","Fra","0102030405","123456789");
	}
}
