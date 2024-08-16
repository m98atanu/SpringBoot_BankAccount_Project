package com.bank.demo.exceptionHandler;

public class InvalidJsonException extends Exception{

	public InvalidJsonException() {
		
	}
	public InvalidJsonException(String errorMessage) {
		super(errorMessage);
	}
}
