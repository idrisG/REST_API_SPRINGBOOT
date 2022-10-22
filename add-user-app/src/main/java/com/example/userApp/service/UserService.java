package com.example.userApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userApp.model.User;
import com.example.userApp.repository.UserRepository;
/**
 * UserService class to accede to UserRepository and take action on it
 * @author idris
 *
 */
@Service("userService")
public class UserService {

	private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	/**
	 * Find user by username in repository (database)
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	/**
	 * Save user in database
	 * @param user
	 * @return user
	 */
	public User createUser(User user) {
		userRepository.save(user);
		return user;
	}
	
}
