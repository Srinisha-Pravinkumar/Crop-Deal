package com.login.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AuthDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidAuthDto() {
        AuthDto authDto = new AuthDto();
        authDto.setEmail("valid.email@example.com");
        authDto.setPassword("password123");

        Set<ConstraintViolation<AuthDto>> violations = validator.validate(authDto);

        assertTrue(violations.isEmpty(), "There should be no validation errors for valid input");
    }

    @Test
    void testEmptyEmail() {
        AuthDto authDto = new AuthDto();
        authDto.setEmail("");
        authDto.setPassword("password123");

        Set<ConstraintViolation<AuthDto>> violations = validator.validate(authDto);

        assertFalse(violations.isEmpty(), "Empty email should trigger a validation error");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testEmptyPassword() {
        AuthDto authDto = new AuthDto();
        authDto.setEmail("valid.email@example.com");
        authDto.setPassword("");

        Set<ConstraintViolation<AuthDto>> violations = validator.validate(authDto);

        assertFalse(violations.isEmpty(), "Empty password should trigger a validation error");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void testInvalidEmail() {
        AuthDto authDto = new AuthDto();
        authDto.setEmail("invalid-email");
        authDto.setPassword("password123");

        Set<ConstraintViolation<AuthDto>> violations = validator.validate(authDto);

        assertFalse(violations.isEmpty(), "Invalid email format should trigger a validation error");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testBothFieldsEmpty() {
        AuthDto authDto = new AuthDto();
        authDto.setEmail("");
        authDto.setPassword("");

        Set<ConstraintViolation<AuthDto>> violations = validator.validate(authDto);

        assertFalse(violations.isEmpty(), "Both email and password fields are empty, validation should fail");

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }
}
