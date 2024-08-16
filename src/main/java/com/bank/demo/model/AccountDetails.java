package com.bank.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
public class AccountDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int accountId;
	
	private String accountType;
	private double amountAvailable;
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public double getAmountAvailable() {
		return amountAvailable;
	}
	public void setAmountAvailable(double amountAvailable) {
		this.amountAvailable = amountAvailable;
	}
	public AccountDetails(int accountId, String accountType, double amountAvailable) {
		super();
		this.accountId = accountId;
		this.accountType = accountType;
		this.amountAvailable = amountAvailable;
	}
	public AccountDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}

