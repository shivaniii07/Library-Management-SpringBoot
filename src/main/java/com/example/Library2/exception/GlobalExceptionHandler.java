package com.example.Library2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookAlreadyBorrowed.class)
    public ResponseEntity<String> handleBookAlreadyBorrowedException(BookAlreadyBorrowed ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());  // 409 Conflict
    }
}
