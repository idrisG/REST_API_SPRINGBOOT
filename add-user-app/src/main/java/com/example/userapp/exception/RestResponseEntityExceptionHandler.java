package com.example.userapp.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorMessage> handleConflictGetParam(RuntimeException ex, WebRequest request) {
        String messageContent = "Get method takes numeric value as path variable \n";
        ErrorMessage message = new ErrorMessage(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                messageContent+ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<ErrorMessage>(message,HttpStatus.BAD_REQUEST);
    }
}
