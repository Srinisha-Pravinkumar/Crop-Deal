package com.login.controller;

import com.login.dto.AuthDto;
import com.login.dto.userDto;
import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.exception.UserNotFoundException;
import com.login.model.User;
import com.login.service.UserService;
import com.login.security.Jwtutil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private Jwtutil jwtutil;

    private User testUser;
    private AuthDto loginUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1, "John Doe", "john@example.com", "password", "1234567890", "Male", "ADMIN", "123 Test Street");
        loginUser = new AuthDto("john@example.com", "password");
    }

    @Test
    public void testRegisterUser_Success() throws UserAlreadyPresentException {
        // Mock behavior
        when(userService.registerUser(Mockito.any())).thenReturn(testUser);

        ResponseEntity<String> response = userController.registerUser(testUser);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(testUser.getEmailId(), response.getBody());
        verify(userService, times(1)).registerUser(Mockito.any());
    }

//    @Test
//    public void testRegisterUser_UserAlreadyExists() throws UserAlreadyPresentException {
//        when(userService.registerUser(Mockito.any())).thenThrow(UserAlreadyPresentException.class);
//
//        ResponseEntity<String> response = userController.registerUser(testUser);
//
//        assertEquals(409, response.getStatusCodeValue());
//        verify(userService, times(1)).registerUser(Mockito.any());
//    }

    @Test
    public void testRegisterUser_UserAlreadyExists() throws UserAlreadyPresentException {
        // Fixed: Provide an exception instance instead of the class
        when(userService.registerUser(Mockito.any())).thenThrow(new UserAlreadyPresentException("User already exists"));
 
        // Assert exception is thrown
        UserAlreadyPresentException exception = assertThrows(UserAlreadyPresentException.class,
                () -> userController.registerUser(testUser));
 
        // Verify the exception message
        assertEquals("User already exists", exception.getMessage());
        
        // Verify service call
        verify(userService, times(1)).registerUser(Mockito.any());
    }
    @Test
    public void testLoginUser_Success() throws InvalidCredentialException {
        userDto userDtoResponse = new userDto();
        userDtoResponse.setUserId(1);
        userDtoResponse.setUsername("John Doe");
        userDtoResponse.setRole("ADMIN");
        userDtoResponse.setAccessToken("token");
//        userDtoResponse.setAddress("123 Test Street");

        when(userService.login(Mockito.any())).thenReturn(userDtoResponse);

        ResponseEntity<Object> response = userController.loginUser(loginUser);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).login(Mockito.any());
    }

    @Test
    public void testLoginUser_InvalidCredentials() throws InvalidCredentialException {
        when(userService.login(Mockito.any())).thenThrow(new InvalidCredentialException("Invalid credentials"));

        assertThrows(InvalidCredentialException.class, () -> userController.loginUser(loginUser));
        verify(userService, times(1)).login(Mockito.any());
    }

    @Test
    public void testGetAllProfiles() {
        List<User> users = Arrays.asList(testUser, new User(2, "Alice", "alice@example.com", "pass123", "1234567890", "Female", "ADMIN", "456 Test Blvd"));
        
        when(userService.getAllProfiles()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllProfiles();
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).getAllProfiles();
    }

    @Test
    public void testGetByProfileId_Success() throws UserNotFoundException {
        when(userService.getById(1)).thenReturn(testUser);

        ResponseEntity<User> response = userController.getByProfileId(1);

        assertNotNull(response.getBody());
        assertEquals(testUser.getFullName(), response.getBody().getFullName());
        verify(userService, times(1)).getById(1);
    }

    @Test
    public void testGetByProfileId_NotFound() throws UserNotFoundException {
        when(userService.getById(1)).thenThrow(new UserNotFoundException("User not found"));

        assertThrows(UserNotFoundException.class, () -> userController.getByProfileId(1));
        verify(userService, times(1)).getById(1);
    }

    @Test
    public void testUpdateProfile_Success() throws UserNotFoundException {
        User updatedUser = new User(1, "John Doe Updated", "john_updated@example.com", "newpass", "1234567890", "Male", "ADMIN", "123 New Address");

        when(userService.getById(1)).thenReturn(testUser);
        doNothing().when(userService).updateProfile(updatedUser);

        ResponseEntity<Void> response = userController.updateProfile(1, updatedUser);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).updateProfile(Mockito.any());
    }

    @Test
    public void testDeleteProfile_Success() throws UserNotFoundException {
        when(userService.getById(1)).thenReturn(testUser);
        doNothing().when(userService).deleteProfile(1);

        ResponseEntity<Void> response = userController.deleteProfile(1);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).deleteProfile(1);
    }

    @Test
    public void testGetByEmailId() {
        when(userService.findByEmailId("john@example.com")).thenReturn(testUser);

        ResponseEntity<User> response = userController.getProfileByEmailId("john@example.com");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("john@example.com", response.getBody().getEmailId());
        verify(userService, times(1)).findByEmailId("john@example.com");
    }

    @Test
    public void testGetUsersByRole() {
        List<User> users = Arrays.asList(testUser);
        when(userService.getUsersByRole("ADMIN")).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getUsersByRole("ADMIN");

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).getUsersByRole("ADMIN");
    }
}
