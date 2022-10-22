package com.example.userApp.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

/**
 * ResponseStatusException adding message from BindingResult
 * @author idris
 *
 */
//TODO create Util class with public static messageFromBindingResult to use directly in controller
public class UserFormException extends ResponseStatusException {

	private static final long serialVersionUID = 5579571395494894023L;
	/**
	 * Constructor
	 * @param badRequest
	 * @param result
	 */
	public UserFormException(HttpStatus badRequest, BindingResult result) {
		super(badRequest,messageFromBindingResult(result));
	}
	/**
	 * Message stored in bindingResult errros
	 * @param result binding result
	 * @return message
	 */
	private static String messageFromBindingResult(BindingResult result) {
		String message =  result.getAllErrors().stream()
        		.map(e -> e.getDefaultMessage()).collect(Collectors.joining());
		return message;
	}
}
