package com.bank.demo.exceptionHandler;

//to present exception in JSON
public class ExceptionResponse {
	
	private String errorMessage;
	private String requestedUri;
	private String requestedMethod;
	private int serverPort;
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public void setRequestedMethod(String requestedMethod) {
		this.requestedMethod = requestedMethod;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void setRequestedUri(String requestedUri) {
		this.requestedUri = requestedUri;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public String getRequestedUri() {
		return requestedUri;
	}
	public String getRequestedMethod() {
		return requestedMethod;
	}
	public int getServerPort() {
		return serverPort;
	}
	
	
	
}
