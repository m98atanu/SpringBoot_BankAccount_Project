package com.bank.demo.DTO;

public class CustomerNewDetails {
	
	private String mobileNumber;
	private String name;
	private String emailId;
	private long aadharNo;
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public long getAadharNo() {
		return aadharNo;
	}
	public void setAadharNo(long aadharNo) {
		this.aadharNo = aadharNo;
	}
	public CustomerNewDetails(String mobileNumber, String name, String emailId, long aadharNo) {
		super();
		this.mobileNumber = mobileNumber;
		this.name = name;
		this.emailId = emailId;
		this.aadharNo = aadharNo;
	}
	public CustomerNewDetails() {
	}
	
	
	
}
