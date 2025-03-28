package com.login.service;
 
import com.login.dto.AuthDto;
import com.login.dto.userDto;
import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.exception.UserNotFoundException;
import com.login.model.User;
import com.login.repository.UserRepository;
import com.login.security.Jwtutil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
 
import java.util.Arrays;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
@TestMethodOrder(OrderAnnotation.class)
public class UserServiceImplTest {
 
    @InjectMocks
    private UserService userService;
 
    @Mock
    private UserRepository userRepository;
 
    @Mock
    private PasswordEncoder passwordEncoder;
 
    @Mock
    private AuthenticationManager authenticationManager;
 
    @Mock
    private Jwtutil jwtutil;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    @Order(1)
    public void testRegisterUser_Success() throws UserAlreadyPresentException {
        User user = new User();
        user.setEmailId("test@example.com");
        user.setPassword("password");
 
        when(userRepository.existsByEmailIdIgnoreCase(user.getEmailId())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
 
        User result = userService.registerUser(user);
 
        assertTrue(result != null);
        verify(userRepository).save(user);
    }
 
    @Test
    @Order(2)
    public void testRegisterUser_UserAlreadyPresent() throws UserAlreadyPresentException {
        User user = new User();
        user.setEmailId("test@example.com");
 
        when(userRepository.existsByEmailIdIgnoreCase(user.getEmailId())).thenReturn(true);
 
        assertThrows(UserAlreadyPresentException.class, () -> userService.registerUser(user));
        verify(userRepository, never()).save(user);
    }
 
//    @Test
//    @Order(3)
//    public void testLogin_Success() throws InvalidCredentialException {
//        AuthDto authDto = new AuthDto("test@example.com", "password");
//        User user = new User();
//        user.setPassword("encodedPassword");  // Set encoded password here
//        user.setId(1);
//        user.setRole("USER");
//        user.setFullName("Test User");
// 
//        // Mocking AuthenticationManager and Jwtutil
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword());
//        Authentication authentication = mock(Authentication.class);
//        when(authenticationManager.authenticate(authToken)).thenReturn(authentication);
//        when(authentication.getPrincipal()).thenReturn(user);
//        when(jwtutil.generateToken(user.getFullName(), user.getRole(), user.getId(), user.getEmailId())).thenReturn("token");
// 
//        userDto response = userService.login(authDto);
// 
//        assertNotNull(response);
//        assertEquals("token", response.getAccessToken());
//        assertEquals(1, response.getUserId());
//        assertEquals("USER", response.getRole());
//        assertEquals("Test User", response.getUsername());
//    }
// 
    @Test
    @Order(4)
    public void testLogin_InvalidCredentials() {
        AuthDto authDto = new AuthDto("test@example.com", "wrongPassword");
 
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword());
        when(authenticationManager.authenticate(authToken)).thenThrow(BadCredentialsException.class);
 
        assertThrows(InvalidCredentialException.class, () -> userService.login(authDto));
    }
 
    @Test
    @Order(5)
    public void testGetAllProfiles() {
        User user = new User();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
 
        assertEquals(1, userService.getAllProfiles().size());
        assertEquals(user, userService.getAllProfiles().get(0));
    }
 
    @Test
    @Order(6)
    public void testGetByProfileId_Success() throws UserNotFoundException {
        User user = new User();
        user.setId(1);
 
        when(userRepository.findById(1)).thenReturn((user));
 
        User result = userService.getById(1);
 
        assertNotNull(result);
        assertEquals(1, result.getId());
    }
 
//    @Test
//    @Order(7)
//    public void testGetByProfileId_NotFound() throws UserNotFoundException {
//        when(userRepository.findById(1));
// 
//        assertThrows(UserNotFoundException.class, () -> userService.getById(1));
//    }
 
//    @Test
//    @Order(8)
//    public void testUpdateProfile_Success() throws UserNotFoundException {
//        User existingUser = new User();
//        existingUser.setId(1);
// 
//        User updatedUser = new User();
//        updatedUser.setId(1);
// 
//        when(userRepository.findById(1));
//        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
// 
//        userService.updateProfile(updatedUser);
// 
//        verify(userRepository).save(updatedUser);
//    }
 
//    @Test
//    @Order(9)
//    public void testUpdateProfile_NotFound() throws UserNotFoundException {
//        User updatedUser = new User();
//        updatedUser.setId(1);
// 
//        when(userRepository.findById(1));
// 
//        assertThrows(UserNotFoundException.class, () -> userService.updateProfile(updatedUser));
//    }
 
//    @Test
//    @Order(10)
//    public void testDeleteProfile_Success() throws UserNotFoundException {
//        User user = new User();
//        user.setId(1);
// 
//        when(userRepository.findById(1));
// 
//        userService.deleteProfile(1);
// 
//        verify(userRepository).deleteById(1);
//    }
 
//    @Test
//    @Order(11)
//    public void testDeleteProfile_NotFound() throws UserNotFoundException {
//        when(userRepository.findById(1));
// 
//        assertThrows(UserNotFoundException.class, () -> userService.deleteProfile(1));
//        verify(userRepository, never()).deleteById(1);
//    }
 
    @Test
    @Order(12)
    public void testGetByFullName_Success() {
        User user = new User();
        user.setFullName("Test User");
 
        when(userRepository.findByFullName("Test User")).thenReturn(user);
 
        User result = userService.getByFullName("Test User");
 
        assertNotNull(result);
        assertEquals("Test User", result.getFullName());
    }
 
//    @Test
//    @Order(13)
//    public void testGetByFullName_NotFound() {
//        when(userRepository.findByFullName("Test User")).thenReturn(null);
// 
//        assertThrows(UserNotFoundException.class, () -> userService.getByFullName("Test User"));
//    }
}