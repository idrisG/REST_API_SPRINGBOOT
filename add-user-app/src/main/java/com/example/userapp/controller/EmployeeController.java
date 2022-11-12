package com.example.userapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.userapp.annotation.Log;
import com.example.userapp.exception.CustomFormException;
import com.example.userapp.model.Employee;
import com.example.userapp.service.EmployeeService;

@RestController
public class EmployeeController {
	/**
	 * Injection of employeeService
	 */
	@Autowired
	private EmployeeService employeeService;
	
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
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee, BindingResult bindingResult) {
    	employee = employeeService.createEmployee(employee);
    	if(employee == null) {
    		bindingResult.reject("username.invalid", "Username already used ! ");
    		throw new CustomFormException(HttpStatus.BAD_REQUEST, bindingResult);
    	}
        if (bindingResult.hasErrors()) {
        	throw new CustomFormException(HttpStatus.BAD_REQUEST, bindingResult);
        }        
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }
}
