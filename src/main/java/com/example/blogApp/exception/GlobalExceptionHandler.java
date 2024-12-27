package com.example.blogApp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHnadler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(Map.of("message",message,"status","failed"),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> dataNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> messageMap = new HashMap<>();
        ex.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            messageMap.put(fieldName,message);
        });
        return new ResponseEntity<>(messageMap,HttpStatus.BAD_REQUEST);
    }
}
