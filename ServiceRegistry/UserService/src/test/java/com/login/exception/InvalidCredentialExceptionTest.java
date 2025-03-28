package com.login.exception;
 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
 
public class InvalidCredentialExceptionTest {
 
    @Test
    void testConstructorMessage() {
        // Given
        String errorMessage = "Invalid username or password";
 
        // When
        InvalidCredentialException exception = new InvalidCredentialException(errorMessage);
 
        // Then
        assertEquals(errorMessage, exception.getMessage(), "Exception message should match the provided message");
    }
 
    @Test
    void testExceptionInheritance() {
        // Given
        String errorMessage = "Invalid login credentials";
 
        // When
        InvalidCredentialException exception = new InvalidCredentialException(errorMessage);
 
        // Then
        assertTrue(exception instanceof Exception, "InvalidCredentialException should be an instance of Exception");
        assertTrue(exception instanceof Throwable, "InvalidCredentialException should be an instance of Throwable");
    }
}