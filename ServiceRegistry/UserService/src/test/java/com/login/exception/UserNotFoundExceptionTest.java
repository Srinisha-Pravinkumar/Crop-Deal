package com.login.exception;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
 
public class UserNotFoundExceptionTest {
 
    @Test
    void testConstructorMessage() {
        // Given
        String errorMessage = "User not found in the system";
 
        // When
        UserNotFoundException exception = new UserNotFoundException(errorMessage);
 
        // Then
        assertEquals(errorMessage, exception.getMessage(), "Exception message should match the provided message");
    }
 
    @Test
    void testExceptionInheritance() {
        // Given
        String errorMessage = "User does not exist";
 
        // When
        UserNotFoundException exception = new UserNotFoundException(errorMessage);
 
        // Then
        assertTrue(exception instanceof Exception, "UserNotFoundException should extend the Exception class");
        assertTrue(exception instanceof Throwable, "UserNotFoundException should be an instance of Throwable");
    }
}