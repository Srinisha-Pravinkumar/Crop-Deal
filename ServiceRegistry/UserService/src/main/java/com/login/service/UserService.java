package com.login.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.login.dto.AuthDto;
import com.login.dto.userDto;
import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.exception.UserNotFoundException;
import com.login.model.User;
import com.login.repository.UserRepository;
import com.login.security.Jwtutil;

@Service
public class UserService{

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Jwtutil jwtutil;

    @Autowired
    private UserRepository userRepository;

  
    public User registerUser(User user) throws UserAlreadyPresentException {
        if (userRepository.existsByEmailIdIgnoreCase(user.getEmailId())) {
            logger.info("User with email {} already exists.", user.getEmailId());
            throw new UserAlreadyPresentException("Can't add user. Already exists by Email.");
        }
        if (userRepository.existsByMobileNumber(user.getMobileNumber())) {
            logger.info("User with Mobile Number  {} already exists.", user.getMobileNumber());
            throw new UserAlreadyPresentException("Can't add user. Already exists by MobileNo.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.info("User with email {} registered successfully.", user.getEmailId());
        return user; 
    }

 
    public int generateUniqueRandomId() {
        Random random = new Random();
        int id;
        do {
            id = random.nextInt(999999) + 1;
        } while (userRepository.existsById(id));
        return id;
    }

   
    public userDto login(AuthDto loginUser) throws InvalidCredentialException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
            if (authentication.isAuthenticated()) {
                User user = userRepository.findByEmailId(loginUser.getEmail());
                if (user == null) {
                    throw new InvalidCredentialException("User not found");
                }
                String token = jwtutil.generateToken(user.getFullName(), user.getRole(), user.getId(), user.getEmailId());
                userDto loginResponse = new userDto();
                loginResponse.setUserId(user.getId());
                loginResponse.setAccessToken(token);
                loginResponse.setRole(user.getRole());
                loginResponse.setUsername(user.getFullName());
                logger.info("User with email {} logged in successfully.", loginUser.getEmail());
                return loginResponse;
            } else {
                throw new InvalidCredentialException("Invalid Email or password");
            }
        } catch (BadCredentialsException e) {
            logger.error("Login attempt failed for email {}", loginUser.getEmail(), e);
            throw new InvalidCredentialException("Invalid Email or password");
        }
    }


    public void updateProfile(User user) throws UserNotFoundException {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findById(user.getId()));
        if (existingUser.isPresent()) {
            userRepository.save(user);
            logger.info("User profile with ID {} updated successfully.", user.getId());
        } else {
            throw new UserNotFoundException("User not found with profile ID: " + user.getId());
        }
    }

    
    public void deleteProfile(int profileId) throws UserNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findById(profileId));
        if (user.isPresent()) {
            userRepository.deleteById(profileId);
            logger.info("User profile with ID {} deleted successfully.", profileId);
        } else {
            throw new UserNotFoundException("User not found with profile ID: " + profileId);
        }
    }

   
    public List<User> getAllProfiles() {
        return userRepository.findAll();
    }

 
    public User getById(int profileId) throws UserNotFoundException {
        return Optional.ofNullable(userRepository.findById(profileId))
                       .orElseThrow(() -> new UserNotFoundException("User not found with profile ID: " + profileId));
    }

   
    public User findByNameOrEmail(String fullName, String email) {
        return userRepository.findByFullNameOrEmailId(fullName, email);
    }

 
    public List<User> searchUsers(String fullName, String email, String mobileNumber) {
        return userRepository.searchUsers(fullName, email, mobileNumber);
    }

 
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRoleIgnoreCase(role);
    }

  
    public User getByFullName(String fullName) {
        return userRepository.findByFullName(fullName);
    }

  
    public User findByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber);
    }


    public User findByEmailId(String emailId) {
        return userRepository.findByEmailId(emailId);
    }
}
