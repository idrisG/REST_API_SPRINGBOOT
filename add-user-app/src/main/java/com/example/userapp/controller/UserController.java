package com.example.userapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.userapp.annotation.Log;
import com.example.userapp.dto.UserDTO;
import com.example.userapp.service.UserService;
import com.example.userapp.validator.UserValidator;

/**
 * Rest controller of our API
 * 
 * @author idris
 *
 */
@RestController
@CrossOrigin("http://localhost:4200")
public class UserController {
	/** Injection of userService*/
	@Autowired
    private UserService userService;

    /**
     * Binds custom UserValidator
     * 
     * @param binder
     */
    @InitBinder(value = "userDTO")
    protected void initBinder(WebDataBinder binder) { //TODO create custom validator using AOP for userDTO so we don't need to binde validator
        binder.addValidators(new UserValidator());
    }

    /**
     * Create user and register in database if user is Validated by our custom
     * UserValidator
     * 
     * @param user
     * @param bindingResult
     * @return
     * @throw {@link com.example.userapp.controller.CustomFormException CustomFormException}
     */
    @Log
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomFormException(HttpStatus.BAD_REQUEST, bindingResult);
        }
        userDTO = userService.createUser(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieve user information using the path variable username if the username is
     * found in database
     * 
     * @param username
     * @return
     * @throw ResponseStatusException
     */
    @Log
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> retireveUser(@PathVariable(value = "id") @Valid Integer id) {
        UserDTO userDTO = userService.findById(id);
        if (userDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User : " + id + " Not Found");
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    
    /**
     * Retrieve all users found in database
     * 
     * @return
     * @throw ResponseStatusException
     */
    @Log
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> retrieveAllUsers(){
    	return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }
    
}
