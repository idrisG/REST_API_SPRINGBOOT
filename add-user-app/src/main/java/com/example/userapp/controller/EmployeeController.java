package com.example.userapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.userapp.annotation.Log;
import com.example.userapp.dto.EmployeeDTO;
import com.example.userapp.model.JwtResponse;
import com.example.userapp.model.LoginRequest;
import com.example.userapp.service.EmployeeService;

/**
 * Rest controller of our API for employee
 * 
 * @author idris
 *
 */
@RestController
@CrossOrigin("http://localhost:4200")
public class EmployeeController {
	/**
	 * Injection of employeeService
	 */
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	/**
     * Get all usernames of employee registered in database
     * returns ResponseEntity with http status OK since FOUND is used for redirect
     * @return
     */
    @Log
    @GetMapping("/employees")
    public ResponseEntity<List<String>> getEmployees() {      
        return new ResponseEntity<>(employeeService.findAllUsername(), HttpStatus.OK);
    }
    
    /**
     * Create employee and register in database if employee is Valid
     * and employee.username isn't already used
     * 
     * @param employee
     * @param bindingResult
     * @return
     * @throw CustomFormException
     */
    @Log
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        	throw new CustomFormException(HttpStatus.BAD_REQUEST, bindingResult);
        }
        try {
        	employeeDTO = employeeService.createEmployee(employeeDTO);
        	return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
        } catch(IllegalArgumentException e) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    /**
     * login of user, if user credentials are validated then return a JwtResponse
     * @return 
     */
    @Log
    @PostMapping("/employees/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    	return new ResponseEntity<>(employeeService.login(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
    					loginRequest.getUsername(), loginRequest.getPassword()))),HttpStatus.OK);
    	
    }
}
