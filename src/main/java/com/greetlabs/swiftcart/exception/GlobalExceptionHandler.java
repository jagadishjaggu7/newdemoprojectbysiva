package com.greetlabs.swiftcart.exception;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public String IllegalArgument(UserAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String UserNotFoundWithEmail(UserNotFoundException ex) {
        return ex.getMessage();

    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String, String>> handleSignatureException(SignatureException ex) {
        // Log the error for debugging purposes
        System.err.println("Invalid JWT signature: " + ex.getMessage());

        // Create a response map with a custom message and the status code
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid JWT signature");
        response.put("message", ex.getMessage());

        // Return the response with a 401 Unauthorized status
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String, String>> handleJwtException(JwtException ex) {
        // Log the error for debugging purposes
        System.err.println("JWT error: " + ex.getMessage());

        // Create a response map with a custom message and the status code
        Map<String, String> response = new HashMap<>();
        response.put("error", "JWT error");
        response.put("message", ex.getMessage());

        // Return the response with a 401 Unauthorized status
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        // Log the error for debugging purposes
        System.err.println("Unexpected error: " + ex.getMessage());

        // Create a response map with a generic error message
        Map<String, String> response = new HashMap<>();
        response.put("error", "Unexpected error occurred");
        response.put("message", ex.getMessage());

        // Return a generic error response with 500 Internal Server Error
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
