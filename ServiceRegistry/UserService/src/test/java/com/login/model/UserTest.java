package com.login.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUser() {
        User user = new User();
        user.setFullName("John Doe");
        user.setEmailId("john.doe@example.com");
        user.setPassword("password123");
        user.setMobileNumber("1234567890");
        user.setGender("Male");
        user.setRole("ADMIN");
        user.setAddress("123 Main Street, City, Country");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty(), "There should be no validation errors for a valid user");
    }

    @Test
    void testBlankFullName() {
        User user = new User();
        user.setFullName("");
        user.setEmailId("john.doe@example.com");
        user.setPassword("password123");
        user.setMobileNumber("1234567890");
        user.setGender("Male");
        user.setRole("ADMIN");
        user.setAddress("123 Main Street, City, Country");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Blank full name should trigger a validation error");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fullName")));
    }

    @Test
    void testInvalidEmail() {
        User user = new User();
        user.setFullName("John Doe");
        user.setEmailId("invalid-email");
        user.setPassword("password123");
        user.setMobileNumber("1234567890");
        user.setGender("Male");
        user.setRole("ADMIN");
        user.setAddress("123 Main Street, City, Country");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Invalid email should trigger a validation error");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("emailId")));
    }

    @Test
    void testInvalidMobileNumber() {
        User user = new User();
        user.setFullName("John Doe");
        user.setEmailId("john.doe@example.com");
        user.setPassword("password123");
        user.setMobileNumber("12345"); // Invalid mobile number
        user.setGender("Male");
        user.setRole("ADMIN");
        user.setAddress("123 Main Street, City, Country");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Invalid mobile number should trigger a validation error");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("mobileNumber")));
    }

    @Test
    void testEmptyPassword() {
        User user = new User();
        user.setFullName("John Doe");
        user.setEmailId("john.doe@example.com");
        user.setPassword(""); // Empty password
        user.setMobileNumber("1234567890");
        user.setGender("Male");
        user.setRole("ADMIN");
        user.setAddress("123 Main Street, City, Country");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Empty password should trigger a validation error");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void testInvalidRole() {
        User user = new User();
        user.setFullName("John Doe");
        user.setEmailId("john.doe@example.com");
        user.setPassword("password123");
        user.setMobileNumber("1234567890");
        user.setGender("Male");
        user.setRole("INVALID_ROLE"); // Invalid role
        user.setAddress("123 Main Street, City, Country");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Invalid role should trigger a validation error");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("role")));
    }
    
    
    @Test
    void testShortAddress() {
        User user = new User();
        user.setFullName("John Doe");
        user.setEmailId("john.doe@example.com");
        user.setPassword("password123");
        user.setMobileNumber("1234567890");
        user.setGender("Male");
        user.setRole("ADMIN");
        user.setAddress("Short"); // Less than 10 characters to trigger validation
 
        Set<ConstraintViolation<User>> violations = validator.validate(user);
 
        // Debugging - Print violations to check why the test might fail
        violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
 
        // Assert that there are validation errors
        assertFalse(violations.isEmpty(), "Address with less than 10 characters should trigger a validation error");
 
        // Check specifically for address field validation
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("address")),
                "Expected validation error for address field");
    }

//    @Test
//    void testShortAddress() {
//        User user = new User();
//        user.setFullName("John Doe");
//        user.setEmailId("john.doe@example.com");
//        user.setPassword("password123");
//        user.setMobileNumber("1234567890");
//        user.setGender("Male");
//        user.setRole("ADMIN");
//        user.setAddress("Short Address");
//
//        Set<ConstraintViolation<User>> violations = validator.validate(user);
//
//        assertFalse(violations.isEmpty(), "Address with less than 20 characters should trigger a validation error");
//        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("Address")));
//    }
}
