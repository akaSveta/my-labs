package com.goncharova.labs.controller;

import com.goncharova.labs.dto.UniversalResponse;
import com.goncharova.labs.exceptions.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<UniversalResponse<Object>> handleBaseException(BaseException ex) {
        log.error("Handling BaseException: {}", ex.getMessage());
        UniversalResponse<Object> response = new UniversalResponse<>(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpCode()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UniversalResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        log.error("Validation error: {}", message);
        UniversalResponse<Object> response = new UniversalResponse<>(4001, message);
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UniversalResponse<Object>> handleGeneralException(Exception ex) {
        log.error("Handling general exception", ex);
        UniversalResponse<Object> response = new UniversalResponse<>(5000, "Internal Server Error: " + ex.getMessage());
        return ResponseEntity.status(500).body(response);
    }
}

