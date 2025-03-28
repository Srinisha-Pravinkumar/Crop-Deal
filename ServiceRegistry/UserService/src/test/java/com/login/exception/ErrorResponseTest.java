package com.login.exception;
 
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;

import com.login.exception.ErrorResponse;
import com.login.exception.UserAlreadyPresentException;

import static org.junit.jupiter.api.Assertions.*;
 
public class ErrorResponseTest {
 
    @Test

    void testErrorResponseConstructorAndGetters() {

        // Given

        HttpStatus status = HttpStatus.NOT_FOUND;

        String message = "Resource not found";
 
        // When

        ErrorResponse errorResponse = new ErrorResponse(status, message);
 
        // Then

        assertEquals(status, errorResponse.getStatus(), "Status should match");

        assertEquals(message, errorResponse.getMessage(), "Message should match");

    }
 
    @Test

    void testErrorResponseDataIntegrity() {

        // Given

        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = "Invalid input";
 
        // When

        ErrorResponse errorResponse = new ErrorResponse(status, message);
 
        // Then

        assertNotNull(errorResponse, "ErrorResponse object should not be null");

        assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getStatus(), "Status should be BAD_REQUEST");

        assertEquals("Invalid input", errorResponse.getMessage(), "Message should be 'Invalid input'");

    }

}

 
