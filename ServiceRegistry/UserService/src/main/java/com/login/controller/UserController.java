package com.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.login.dto.AuthDto;
import com.login.dto.userDto;
import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.exception.UserNotFoundException;
import com.login.model.User;
import com.login.security.Jwtutil;
import com.login.service.UserService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/profiles")

@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService; 

    @Autowired
    private Jwtutil jwtutil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid User user) throws UserAlreadyPresentException {
        user.setId(userService.generateUniqueRandomId());
        if (userService.registerUser(user) != null) {
            return new ResponseEntity<>(user.getEmailId(), HttpStatus.CREATED);
        } else {
            logger.error("User registration failed for email: {}", user.getEmailId());
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid AuthDto loginUser) throws InvalidCredentialException {
        logger.info("Attempting to log in user: {}", loginUser.getEmail());
        try {
            userDto responseObj = userService.login(loginUser);
            logger.info("User logged in successfully: {}", loginUser.getEmail());
            return generateResponse("Login successful", HttpStatus.OK, responseObj);
        } catch (InvalidCredentialException e) {
            logger.error("Invalid credentials for user: {}", loginUser.getEmail(), e);
            throw e;
        }
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Void> updateProfile(@PathVariable int profileId, @RequestBody User user) throws UserNotFoundException {
        User existingProfile = userService.getById(profileId);
        if (existingProfile != null) {
            user.setId(existingProfile.getId());
            userService.updateProfile(user);
            return ResponseEntity.ok().build();
        } else {
            logger.error("Profile not found for update with ID: {}", profileId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllProfiles() {
        List<User> profiles = userService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/getUser/{profileId}")
    public ResponseEntity<User> getByProfileId(@PathVariable int profileId) throws UserNotFoundException {
        User profile = userService.getById(profileId);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            logger.error("Profile with ID {} not found", profileId);
            throw new UserNotFoundException("Profile with ID " + profileId + " not found");
        }
    }

    @GetMapping("/by-mobile-number/{mobileNumber}")
    public ResponseEntity<User> getProfileByMobileNumber(@PathVariable String mobileNumber) {
        User profile = userService.findByMobileNumber(mobileNumber);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            logger.error("Profile with mobile number {} not found", mobileNumber);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable int profileId) throws UserNotFoundException {
        User existingProfile = userService.getById(profileId);
        if (existingProfile != null) {
            userService.deleteProfile(profileId);
            return ResponseEntity.ok().build();
        } else {
            logger.error("Profile not found for deletion with ID: {}", profileId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getfullName/{fullName}")
    public ResponseEntity<User> getProfileByFullName(@PathVariable String fullName) {
        User profile = userService.getByFullName(fullName);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            logger.error("Profile with full name '{}' not found", fullName);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-email/{emailId}")
    public ResponseEntity<User> getProfileByEmailId(@PathVariable String emailId) {
        User profile = userService.findByEmailId(emailId);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            logger.error("Profile with email '{}' not found", emailId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        List<User> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    private ResponseEntity<Object> generateResponse(String message, HttpStatus httpStatus, userDto responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("Message", message);
        map.put("Status", httpStatus.value());
        map.put("userId", responseObj.getUserId());
        map.put("username", responseObj.getUsername());
        map.put("Token", responseObj.getAccessToken());
        map.put("role", responseObj.getRole());
        return new ResponseEntity<>(map, httpStatus);
    }
}
