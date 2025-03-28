package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.dao.DataIntegrityViolationException;
import feign.FeignException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Handle NoSuchElementException (e.g., user not found)
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
		ErrorResponse errorResponse = new ErrorResponse("User not foundsss",
				"No User Present with this ID No: " + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	// Handle DataIntegrityViolationException (e.g., unique constraint violation)
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		ErrorResponse errorResponse = new ErrorResponse("Data integrity violation", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// Handle FeignException (e.g., issues with the external service)
	@ExceptionHandler()
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public ResponseEntity<ErrorResponse> handleFeignException(FeignException ex) {
		ErrorResponse errorResponse = new ErrorResponse("External service error", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}

	// Handle all other exceptions (generic handler)
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse("Internal server error", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Handle UserAlreadyExistException (e.g., user already exists)
	@ExceptionHandler(UserAlreadyExistException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleUserAlreadyExist(UserAlreadyExistException ex) {
		ErrorResponse errorResponse = new ErrorResponse("User already exists",ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	// Handle UserAlreadyExistException (e.g., user already exists)
	@ExceptionHandler(EmptyTableException.class)
	@ResponseStatus(HttpStatus.ALREADY_REPORTED)
	public ResponseEntity<ErrorResponse> handleEmptyTableException(EmptyTableException ex) {
		ErrorResponse errorResponse = new ErrorResponse("No users are there",ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.ALREADY_REPORTED);
	}
	
	// Handle MethodArgumentNotValidException (validation errors)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        StringBuilder errorMessage = new StringBuilder();

        // Combine global errors and field errors into one message
        for (ObjectError globalError : globalErrors) {
            errorMessage.append(globalError.getDefaultMessage()).append(" ");
        }

        for (FieldError fieldError : fieldErrors) {
            errorMessage.append("Field '").append(fieldError.getField()).append("': ")
                    .append(fieldError.getDefaultMessage()).append(" ");
        }

        ErrorResponse errorResponse = new ErrorResponse("Validation failed", errorMessage.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
