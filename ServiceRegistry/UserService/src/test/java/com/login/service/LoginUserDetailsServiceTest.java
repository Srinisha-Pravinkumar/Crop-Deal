package com.login.service;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
 
import com.login.model.User;
import com.login.repository.UserRepository;
 
class LoginUserDetailsServiceTest {
 
    @Mock
    private UserRepository userRepository;
 
    @InjectMocks
    private LoginUserDetailsService loginUserDetailsService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testLoadUserByUsername_UserExists() {
        // Arrange
        String username = "testuser@example.com";
        User mockUser = new User();
        mockUser.setEmailId(username);
        mockUser.setPassword("password123");
        mockUser.setRole("USER");
 
        when(userRepository.findByEmailId(username)).thenReturn(mockUser);
 
        // Act
        LoginUserDetails userDetails = (LoginUserDetails) loginUserDetailsService.loadUserByUsername(username);
 
        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertEquals("USER", userDetails.getRole());
        verify(userRepository, times(1)).findByEmailId(username);
    }
 
    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        String username = "nonexistentuser@example.com";
        when(userRepository.findByEmailId(username)).thenReturn(null);
 
        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> loginUserDetailsService.loadUserByUsername(username));
 
        assertEquals("User Not Found: " + username, exception.getMessage());
        verify(userRepository, times(1)).findByEmailId(username);
    }
}

