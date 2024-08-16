package com.bank.demo.exceptionHandler;

public class CustomerNotFoundException extends Exception{
	
	public CustomerNotFoundException(){
		
	}
	
	public CustomerNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
