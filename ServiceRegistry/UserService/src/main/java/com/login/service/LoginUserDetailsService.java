package com.login.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.login.model.User;
import com.login.repository.UserRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginUserDetailsService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("Attempting to load user details for username: {}", username);

		User user = userRepository.findByEmailId(username);

		if (user == null) {
			logger.warn("User not found with username or email: {}", username);
			throw new UsernameNotFoundException("User Not Found: " + username);
		}
		
		logger.info("User found with username/email: {}", username);
		return new LoginUserDetails(user);
	}
}
