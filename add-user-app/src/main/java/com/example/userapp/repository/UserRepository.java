package com.example.userapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.userapp.model.User;
/**
 * User repository extending from CrudRepository
 * @author idris
 *
 */
@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Integer> {
	/**
	 * Find User in database using username
	 * @param username
	 * @return User if found, null otherwise
	 */
	public User findByUsername(String username);
}
