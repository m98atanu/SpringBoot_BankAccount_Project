package com.bank.demo.exceptionHandler;

@SuppressWarnings("serial")
public class BadCustomerCreationRequestException extends Exception {
	
	//to throw in built exception
	public BadCustomerCreationRequestException(){
		
	}
	//to throw custom exception
	public BadCustomerCreationRequestException(String errorMessage){
		super(errorMessage);
	}
}
