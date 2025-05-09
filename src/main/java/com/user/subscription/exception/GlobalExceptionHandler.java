package com.user.subscription.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        Map<String, Object> response = createResponse(ex, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSubscriptionNotFound(SubscriptionNotFoundException ex) {
        Map<String, Object> response = createResponse(ex, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateSubscriptionException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateSubscription(DuplicateSubscriptionException ex) {
        Map<String, Object> response = createResponse(ex, HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    private Map<String, Object> createResponse(RuntimeException ex, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("errorCode", status.value());
        response.put("message", ex.getMessage());
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("errorCode", HttpStatus.BAD_REQUEST.value());
        StringBuilder errorMessages = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessages.append(error.getDefaultMessage()).append("\n");
        }
        response.put("message", errorMessages.toString().trim());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}