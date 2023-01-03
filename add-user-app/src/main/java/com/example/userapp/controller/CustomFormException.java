package com.example.userapp.controller;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

/**
 * ResponseStatusException adding message from BindingResult
 * @see {@link org.springframework.web.server.ResponseStatusException ResponseStatusException}
 * @author idris
 *
 */
public class CustomFormException extends ResponseStatusException {

    private static final long serialVersionUID = 5579571395494894023L;

    /**
     * Exception build using ResponseStatusException as blueprint
     * 
     * @param badRequest
     * @param result
     */
    public CustomFormException(HttpStatus badRequest, BindingResult result) {
        super(badRequest, messageFromBindingResult(result));
    }

    /**
     * Message stored in bindingResult errors
     * 
     * @param result binding result
     * @return message
     */
    private static String messageFromBindingResult(BindingResult result) {
        String message = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining());
        return message;
    }
}
