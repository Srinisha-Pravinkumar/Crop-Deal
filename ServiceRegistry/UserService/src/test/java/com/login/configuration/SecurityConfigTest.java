

 
package com.login.configuration;
 
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.ApplicationContext;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.test.context.ActiveProfiles;
 
import com.login.service.LoginUserDetailsService;
 
import static org.junit.jupiter.api.Assertions.*;
 
@SpringBootTest

@ActiveProfiles("test")

public class SecurityConfigTest {
 
    @Autowired

    private ApplicationContext context;
 
    @Autowired

    private LoginUserDetailsService loginUserDetailsService;
 
    @Autowired

    private PasswordEncoder passwordEncoder;
 
    @Autowired

    private AuthenticationManager authenticationManager;
 
    @Autowired

    private AuthenticationProvider authenticationProvider;
 
    @Autowired

    private SecurityFilterChain securityFilterChain;
 
    @BeforeEach

    void setUp() {

        assertNotNull(context, "Application context should be loaded");

    }
 
    @Test

    void testPasswordEncoderBean() {

        assertNotNull(passwordEncoder, "PasswordEncoder bean should be present");

        String rawPassword = "testPassword";

        String encodedPassword = passwordEncoder.encode(rawPassword);
 
        assertNotEquals(rawPassword, encodedPassword, "Passwords should not match after encoding");

    }
 
    @Test

    void testAuthenticationProviderBean() {

        assertNotNull(authenticationProvider, "AuthenticationProvider bean should be present");

        assertTrue(authenticationProvider instanceof org.springframework.security.authentication.dao.DaoAuthenticationProvider, 

                   "AuthenticationProvider should be an instance of DaoAuthenticationProvider");

    }
 
    @Test

    void testAuthenticationManagerBean() {

        assertNotNull(authenticationManager, "AuthenticationManager bean should be present");

    }
 
    @Test

    void testSecurityFilterChainBean() {

        assertNotNull(securityFilterChain, "SecurityFilterChain bean should be present");

    }
 
    @Test

    void testLoginUserDetailsServiceInjection() {

        assertNotNull(loginUserDetailsService, "LoginUserDetailsService should be injected properly");

    }

}

 
