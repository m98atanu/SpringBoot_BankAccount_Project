package com.bank.demo.exceptionHandler;

public class InsufficientFundException extends Exception{
	
	public InsufficientFundException()
	{
		
	}
	public InsufficientFundException(String errorMessage) {
		super(errorMessage);
	}
}
