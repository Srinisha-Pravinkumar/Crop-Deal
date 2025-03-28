package com.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
	@NotEmpty(message = "Email cannot be empty") // ✅ Added this to prevent empty values
	@Email(message = "Please provide email...")
	private String email;
	@NotEmpty(message = "Please give a password...")
	private String password;
}
