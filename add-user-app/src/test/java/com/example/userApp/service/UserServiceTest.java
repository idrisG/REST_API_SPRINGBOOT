package com.example.userApp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.userApp.model.User;
import com.example.userApp.repository.UserRepository;

/**
 * Unit test class for UserService
 * @author idris
 *
 */
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService ;
	
	String username = "hubert";
	String birthdate="01/01/1996";
	String country = "France";
	String phoneNumber = "0102030405";
	String gender = "male";
	
	private User user = new User(username,birthdate,country,phoneNumber,gender);
	
	/**
	 * Unit test find user by username success
	 */
	@Test
	void testFindByUsername_success() {
		when(userRepository.findByUsername(any(String.class))).thenReturn(user);
		User foundUser = userService.findByUsername(user.getUsername());
		assertNotNull(foundUser);
		assertThat(foundUser.getUsername()).isSameAs(username);
		assertThat(foundUser.getBirthdate()).isSameAs(birthdate);
		assertThat(foundUser.getCountry()).isSameAs(country);
		assertThat(foundUser.getPhoneNumber()).isSameAs(phoneNumber);
		assertThat(foundUser.getGender()).isSameAs(gender);
	}
	
	/**
	 * Unit test save user success
	 */
	@Test
	void testCreateUser_success() {
		when(userRepository.save(any(User.class))).thenReturn(user);
		User savedUser = userService.createUser(user);
		assertNotNull(savedUser);
		assertThat(savedUser.getUsername()).isSameAs(username);
		assertThat(savedUser.getBirthdate()).isSameAs(birthdate);
		assertThat(savedUser.getCountry()).isSameAs(country);
		assertThat(savedUser.getPhoneNumber()).isSameAs(phoneNumber);
		assertThat(savedUser.getGender()).isSameAs(gender);
	}
	
	/**
	 * Unit test find by username failure, should return null user
	 */
	@Test
	void testFindByUsername_failure() {
		when(userRepository.findByUsername(any(String.class))).thenReturn(null);
		User foundUser = userService.findByUsername("wrong username");
		assertNull(foundUser);
	}

}
