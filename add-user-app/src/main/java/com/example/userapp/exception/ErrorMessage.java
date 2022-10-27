package com.example.userapp.exception;

import java.time.Instant;
import org.springframework.http.HttpStatus;

/**
 * Error message class, used to create Message error when handling exception (in
 * ControllerAdvice)
 * 
 * @author idris
 *
 */
public class ErrorMessage {

    private Instant timestamp;
    private int statusCode;
    private HttpStatus status;
    private String message;
    private String description;

    /**
     * Constructor of the Error Message
     * 
     * @param timestamp
     * @param statusCode
     * @param status
     * @param message
     * @param description
     */
    public ErrorMessage(Instant timestamp, int statusCode, HttpStatus status, String message, String description) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}