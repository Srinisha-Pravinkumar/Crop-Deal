package com.example.demo.exception;

public class UserTypeMismatchException extends RuntimeException {
	public UserTypeMismatchException(String message) {
		super(message);
	}
}
