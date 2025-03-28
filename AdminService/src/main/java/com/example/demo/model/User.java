package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data

public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @NotBlank(message = "Please enter user name")
    @Size(min = 2, max = 50, message = "User name must be between 2 and 50 characters")
    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email must not be blank")
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
        message = "Please enter a valid email address"
    )
    @Size(max = 100, message = "Email must be less than 100 characters")
    @Column(name = "email_id", nullable = false, unique = true, length = 100)
    private String emailId;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "Mobile number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
    @Column(name = "mobile_number", nullable = false, length = 10)
    private String mobileNumber;

    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be either Male, Female, or Other")
    @Column(name = "gender", length = 10)
    private String gender;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(ADMIN|FARMER|DEALER)$", message = "Role must be one of OWNER, MANAGER, or RECEPTIONIST")
    @Column(name = "role", nullable = false, length = 20)
    private String role;
    @NotBlank(message = "Address is required")
    @Size(min = 20, message = "Password must be at least 20 characters long")
    @Column(name = "Address", nullable = false)
    private String Address;

	

	
}
