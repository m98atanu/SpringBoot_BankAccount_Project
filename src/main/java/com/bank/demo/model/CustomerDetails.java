package com.bank.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;

@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class CustomerDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerId;
	/*
	 *  before creating or updating my code will be checked for leading +91, 
	 *  as I am  permitting only Indians to create or update bank account;
	 */
	private String mobileNumber;
	private String name;
	private String emailId;
	//aadhar is mandatory
	private long aadharNo;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="customer_id_fk")
	@OrderColumn(name="accounts_key_for_Customer")
	List<AccountDetails> accounts;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

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

	public List<AccountDetails> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountDetails> accounts) {
		this.accounts = accounts;
	}

	public CustomerDetails(int customerId, String mobileNumber, String name, String emailId, long aadharNo,
			List<AccountDetails> accounts) {
		super();
		this.customerId = customerId;
		this.mobileNumber = mobileNumber;
		this.name = name;
		this.emailId = emailId;
		this.aadharNo = aadharNo;
		this.accounts = accounts;
	}

	public CustomerDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
