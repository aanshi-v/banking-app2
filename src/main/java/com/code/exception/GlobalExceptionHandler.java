package com.code.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.code.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<ApiResponse<String>> handleRuntime(RuntimeException ex) {
	        ApiResponse<String> response = new ApiResponse<>("FAILURE", ex.getMessage(), 400, null);
	        return ResponseEntity.badRequest().body(response);
	    }
}
