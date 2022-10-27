package com.example.userapp.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userapp.dto.UserDTO;
import com.example.userapp.model.User;
import com.example.userapp.repository.UserRepository;
/**
 * UserService class to accede to UserRepository and take action on it
 * @author idris
 *
 */
@Service("userService")
public class UserService {

	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;
	/**
	 * Constructor
	 * @param userRepository
	 */
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public UserService(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
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
	 * Save user in database, transform userdto to entity then save then retransform to dto
	 * @param user
	 * @return saved user transformed to DTO
	 */
	public UserDTO createUser(UserDTO userDTO) {
		return entityToDTO(userRepository.save(dtoToEntity(userDTO)));
	}
	/**
	 * 
	 * @param id
	 * @return user found in dto or null if not found
	 */
	public UserDTO findById(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			return null;
		}
		return entityToDTO(user.get());
	}
	
	
	/**
	 * Method that transform an entity to a dto
	 * @param user must never be null
	 * @return
	 */
	private UserDTO entityToDTO(User user) {
		if(user==null) {
			return null;
		}
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		return userDTO;
	}
	/**
	 * Method that transform a dto to an entity
	 * @param userDTO must never be null
	 * @return
	 */
	private User dtoToEntity(UserDTO userDTO) {
		if(userDTO==null) {
			return null;
		}
		User user = modelMapper.map(userDTO, User.class);
		return user;
	}
	
}
