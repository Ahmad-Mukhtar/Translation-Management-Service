package com.digitaltol.translation.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handleApiException(ApiException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(ex.getMessage());
    }
}
