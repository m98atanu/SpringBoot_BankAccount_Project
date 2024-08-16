package com.bank.demo.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
	
	@ExceptionHandler(BadCustomerCreationRequestException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	ExceptionResponse handleBadCustomerCreationRequestException(BadCustomerCreationRequestException exception,
			HttpServletRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setErrorMessage(exception.getMessage());
		exceptionResponse.setRequestedMethod(request.getMethod());
		exceptionResponse.setServerPort(request.getServerPort());
		exceptionResponse.setRequestedUri(request.getRequestURI());
		return exceptionResponse;
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ResponseBody
	ExceptionResponse handleCustomerNotFoundException(CustomerNotFoundException exception,
			HttpServletRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setErrorMessage(exception.getMessage());
		exceptionResponse.setRequestedMethod(request.getMethod());
		exceptionResponse.setServerPort(request.getServerPort());
		exceptionResponse.setRequestedUri(request.getRequestURI());
		return exceptionResponse;
	}
	
	@ExceptionHandler(InvalidJsonException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	ExceptionResponse handleInvalidJsonException(InvalidJsonException exception,
			HttpServletRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setErrorMessage(exception.getMessage());
		exceptionResponse.setRequestedMethod(request.getMethod());
		exceptionResponse.setServerPort(request.getServerPort());
		exceptionResponse.setRequestedUri(request.getRequestURI());
		return exceptionResponse;
	}
	
	@ExceptionHandler(InsufficientFundException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	ExceptionResponse handleInsufficientFundException(InsufficientFundException exception,
			HttpServletRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setErrorMessage(exception.getMessage());
		exceptionResponse.setRequestedMethod(request.getMethod());
		exceptionResponse.setServerPort(request.getServerPort());
		exceptionResponse.setRequestedUri(request.getRequestURI());
		return exceptionResponse;
	}
	
}
