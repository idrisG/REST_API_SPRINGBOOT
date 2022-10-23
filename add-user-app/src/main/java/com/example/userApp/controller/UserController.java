package com.example.userApp.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.userApp.aspect.Log;
import com.example.userApp.exception.UserFormException;
import com.example.userApp.model.User;
import com.example.userApp.service.UserService;
import com.example.userApp.validator.UserValidator;

/**
 * Rest controller of our API
 * @author idris
 *
 */
@RestController
public class UserController {
	
	private UserService userService;
	
	/**
	 * Constructor
	 * @param userService
	 */
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	/**
	 * Binds custom UserValidator
	 * @param binder
	 */
	@InitBinder(value="user")
	protected void initBinder(WebDataBinder binder) {
	    binder.addValidators(new UserValidator());
	}
	/**
	 * Create user and register in database if user is Validated by our custom UserValidator
	 * and user.username isn't already used
	 * @param user
	 * @param bindingResult
	 * @return
	 * @throw UserFormException
	 */
	@Log
	@PostMapping("/createUser")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user, 
			BindingResult bindingResult){
		User userExists = userService.findByUsername(user.getUsername());
		if(userExists != null) 
			bindingResult.rejectValue("username", "username.used","Username already used !");
		if(bindingResult.hasErrors())
			throw new UserFormException(HttpStatus.BAD_REQUEST,bindingResult);
		userService.createUser(user);
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}
	
	/**
	 * Retrieve user information using the path variable username if
	 * the username is found in database
	 * @param username
	 * @return
	 * @throw ResponseStatusException
	 */
	@Log
	@GetMapping("/getUser/{username}")
	public ResponseEntity<User> retireveUser(@PathVariable(value="username") String username) {
		User user = userService.findByUsername(username);
		if(user==null) throw new ResponseStatusException(
		           HttpStatus.NOT_FOUND, "User : "+ username+" Not Found");			
		return new ResponseEntity<>(user, HttpStatus.FOUND);
	}

}
