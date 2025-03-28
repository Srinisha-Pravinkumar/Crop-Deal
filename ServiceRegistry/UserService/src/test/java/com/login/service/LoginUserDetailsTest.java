package com.login.service;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
 
import com.login.model.User;
 
import java.util.Collection;
 
import static org.junit.jupiter.api.Assertions.*;
 
class LoginUserDetailsTest {
 
    private LoginUserDetails loginUserDetails;
 
    @BeforeEach
    void setUp() {
        // Create a User instance
        User user = new User();
        user.setEmailId("testuser@example.com");
        user.setPassword("password123");
        user.setRole("USER");
 
        // Initialize LoginUserDetails with the user instance
        loginUserDetails = new LoginUserDetails(user);
    }
 
    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = loginUserDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER")));
    }
 
    @Test
    void testGetPassword() {
        assertEquals("password123", loginUserDetails.getPassword());
    }
 
    @Test
    void testGetUsername() {
        assertEquals("testuser@example.com", loginUserDetails.getUsername());
    }
 
    @Test
    void testGetRole() {
        assertEquals("USER", loginUserDetails.getRole());
    }
 
    @Test
    void testIsAccountNonExpired() {
        assertTrue(loginUserDetails.isAccountNonExpired());
    }
 
    @Test
    void testIsAccountNonLocked() {
        assertTrue(loginUserDetails.isAccountNonLocked());
    }
 
    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(loginUserDetails.isCredentialsNonExpired());
    }
 
    @Test
    void testIsEnabled() {
        assertTrue(loginUserDetails.isEnabled());
    }
}