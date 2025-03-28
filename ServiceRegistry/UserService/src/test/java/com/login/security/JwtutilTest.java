package com.login.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Date;

public class JwtutilTest {

    private Jwtutil jwtUtil;
    private final String testUserName = "testUser";
    private final String testRole = "ADMIN";
    private final int testId = 123;
    private final String testEmail = "test@example.com";

    @BeforeEach
    void setUp() {
        jwtUtil = new Jwtutil();
    }

    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken(testUserName, testRole, testId, testEmail);

        Assertions.assertNotNull(token, "Generated token should not be null");
        Assertions.assertTrue(jwtUtil.validateToken(token), "Token should be valid");
    }

    @Test
    void testGetUsernameFromToken() {
        String token = jwtUtil.generateToken(testUserName, testRole, testId, testEmail);
        String extractedUsername = jwtUtil.getUsernameFromToken(token);

        Assertions.assertEquals(testUserName, extractedUsername, "Extracted username should match the input username");
    }

    @Test
    void testGetExpirationDateFromToken() {
        String token = jwtUtil.generateToken(testUserName, testRole, testId, testEmail);
        Date expirationDate = jwtUtil.getExpirationDateFromToken(token);

        Assertions.assertNotNull(expirationDate, "Expiration date should not be null");
        Assertions.assertTrue(expirationDate.after(new Date()), "Expiration date should be in the future");
    }

    @Test
    void testValidateToken() {
        String token = jwtUtil.generateToken(testUserName, testRole, testId, testEmail);
        boolean isValid = jwtUtil.validateToken(token);

        Assertions.assertTrue(isValid, "Generated token should be valid");
    }

    @Test
    void testInvalidTokenValidation() {
        String invalidToken = "invalid.token.value";
        boolean isValid = jwtUtil.validateToken(invalidToken);

        Assertions.assertFalse(isValid, "Invalid token should not be validated");
    }
}














//package com.login.security;
// 
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import com.login.security.Jwtutil;
//
//import org.junit.jupiter.api.Assertions;
//import io.jsonwebtoken.Claims;
// 
//public class JwtutilTest {
// 
//    private Jwtutil jwtUtil;
//    private String testUserName = "testUser";
//    private String testRole = "ADMIN";
//    private int testId = 123;
//    private String testEmail = "test@example.com";
// 
//    @BeforeEach
//    void setUp() {
//        jwtUtil = new Jwtutil();
//    }
// 
//    @Test
//    void testGenerateToken() {
//        // Given
//        String token = jwtUtil.generateToken(testUserName, testRole, testId, testEmail);
// 
//        Assertions.assertNotNull(token, "Generated token should not be null");
//        Assertions.assertTrue(jwtUtil.validateToken(token), "Token should be valid");
//    }
// 
//    @Test
//    void testGetUsernameFromToken() {
//        // Given
//        String token = jwtUtil.generateToken(testUserName, testRole, testId, testEmail);
// 
//        // When
//        String extractedUsername = jwtUtil.getUsernameFromToken(token);
// 
//        // Then
//        Assertions.assertEquals(testUserName, extractedUsername, "Extracted username should match the input username");
//    }
// 
//    @Test
//    void testGetExpirationDateFromToken() {
//        // Given
//        String token = jwtUtil.generateToken(testUserName, testRole, testId, testEmail);
// 
//        // When
//        java.util.Date expirationDate = jwtUtil.getExpirationDateFromToken(token);
// 
//        Assertions.assertNotNull(expirationDate, "Expiration date should not be null");
//        Assertions.assertTrue(expirationDate.after(new java.util.Date()), "Expiration date should be in the future");
//    }
// 
//    @Test
//    void testValidateToken() {
//        // Given
//        String token = jwtUtil.generateToken(testUserName, testRole, testId, testEmail);
// 
//        // When
//        boolean isValid = jwtUtil.validateToken(token);
// 
//        // Then
//        Assertions.assertTrue(isValid, "Generated token should be valid");
//    }
// 
//    @Test
//    void testInvalidTokenValidation() {
//        // Given
//        String invalidToken = "invalid.token.value";
// 
//        // When
//        boolean isValid = jwtUtil.validateToken(invalidToken);
// 
//        // Then
//        Assertions.assertFalse(isValid, "Invalid token should not be validated");
//    }
//}