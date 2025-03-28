package com.login.exception;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
 

 
public class UserAlreadyPresentExceptionTest {
 
    @Test

    void testConstructorMessage() {

        // Given

        String errorMessage = "User already exists in the system";
 
        // When

        UserAlreadyPresentException exception = new UserAlreadyPresentException(errorMessage);
 
        // Then

        assertEquals(errorMessage, exception.getMessage(), "Exception message should match the provided message");

    }
 
    @Test

    void testExceptionInheritance() {

        // Given

        String errorMessage = "User already present";
 
        // When

        UserAlreadyPresentException exception = new UserAlreadyPresentException(errorMessage);
 
        // Then

        assertTrue(exception instanceof Exception, "UserAlreadyPresentException should extend the Exception class");

        assertTrue(exception instanceof Throwable, "UserAlreadyPresentException should be an instance of Throwable");

    }

}

 