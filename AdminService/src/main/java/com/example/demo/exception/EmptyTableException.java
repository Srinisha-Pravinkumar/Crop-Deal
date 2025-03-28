package com.example.demo.exception;

public class EmptyTableException extends RuntimeException {
	public EmptyTableException(String message) {
		super(message);
	}
}
