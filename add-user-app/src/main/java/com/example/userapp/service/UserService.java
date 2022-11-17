package com.example.userapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userapp.dto.UserDTO;
import com.example.userapp.mapper.UserMapper;
import com.example.userapp.model.User;
import com.example.userapp.repository.UserRepository;

/**
 * UserService class to accede to UserRepository and take action on it
 * 
 * @author idris
 *
 */
@Service("userService")
public class UserService {
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    /**
     * Find user by username in repository (database)
     * 
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Save user in database, transform userdto to entity then save then retransform
     * to dto
     * 
     * @param user
     * @return saved user transformed to DTO
     */
    public UserDTO createUser(UserDTO userDTO) {
    	return this.userMapper.entityToDTO(userRepository.save(this.userMapper.dtoToEntity(userDTO)));
    }

    /**
     * 
     * @param id
     * @return user found in dto or null if not found
     */
    public UserDTO findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return null;
        }
        return this.userMapper.entityToDTO(user.get());
    }
}
