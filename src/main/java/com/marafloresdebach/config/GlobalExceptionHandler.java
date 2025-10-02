package com.marafloresdebach.config;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArg(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of("status", 409, "error", "Conflict", "message", ex.getMessage()));
  }
}