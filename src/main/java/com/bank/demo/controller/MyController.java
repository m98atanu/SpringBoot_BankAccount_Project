package com.bank.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.demo.DTO.CustomerNewDetails;
import com.bank.demo.DTO.DepositDetails;
import com.bank.demo.DTO.MoneyTransfer;
import com.bank.demo.exceptionHandler.BadCustomerCreationRequestException;
import com.bank.demo.exceptionHandler.CustomerNotFoundException;
import com.bank.demo.exceptionHandler.InsufficientFundException;
import com.bank.demo.model.CustomerDetails;
import com.bank.demo.service.BankServiceImpl;

@RestController
@RequestMapping("/banking")
public class MyController {
	
	@Autowired
	BankServiceImpl bankService;
	
	@PostMapping("/addCustomer")
	public ResponseEntity<Integer> addAccount(@RequestBody CustomerDetails customer) throws BadCustomerCreationRequestException{
		return new ResponseEntity<Integer>(bankService.addAccount(customer), HttpStatus.CREATED);
	}

	@PutMapping("/updateCustomer")
	public ResponseEntity updateCustomerDetails(@RequestBody CustomerNewDetails customerNewDetails) throws Exception{
		bankService.UpdateCustomerPersonalDetails(customerNewDetails);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<CustomerDetails> getCustomer(@PathVariable int customerId) throws Exception {
		return new ResponseEntity<>(bankService.fetchById(customerId),HttpStatus.OK);
	}
	
	@GetMapping("/allCustomer")
	public ResponseEntity<List<CustomerDetails>> getAllCustomer(){
		return new ResponseEntity<>(bankService.fetchAllCustomer(), HttpStatus.OK);
	}
	
	@PutMapping("/transfer")
	public ResponseEntity transferMoney(@RequestBody MoneyTransfer moneyTransfer) throws CustomerNotFoundException, InsufficientFundException {
		bankService.transferMoney(moneyTransfer);
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/deposit")
	public ResponseEntity depositMoney(@RequestBody DepositDetails depositDetails) throws Exception{
		bankService.deposit(depositDetails.getAccountId(), depositDetails.getAmount());
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
}
