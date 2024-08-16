package com.bank.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.demo.DTO.CustomerNewDetails;
import com.bank.demo.DTO.MoneyTransfer;
import com.bank.demo.exceptionHandler.BadCustomerCreationRequestException;
import com.bank.demo.exceptionHandler.CustomerNotFoundException;
import com.bank.demo.exceptionHandler.InsufficientFundException;
import com.bank.demo.exceptionHandler.InvalidJsonException;
import com.bank.demo.model.AccountDetails;
import com.bank.demo.model.CustomerDetails;
import com.bank.demo.repository.AccountRepository;
import com.bank.demo.repository.CustomerRepository;

@Service
public class BankServiceImpl {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private AccountRepository accountRepo;
	
	private static final Logger logger = Logger.getLogger(BankServiceImpl.class.getName());
	
	//Creation of account
	public int addAccount(CustomerDetails customer) throws BadCustomerCreationRequestException{
		Optional<CustomerDetails> customerInDB = customerRepo.findByAadharNo(customer.getAadharNo());
		boolean accountCreatable = false;
		if(customerInDB.isEmpty() && customer.getAccounts().size()<=2) {
			if (customer.getAccounts().size() == 1 
					&& customer.getAccounts().get(0).getAccountType().equals("Current") || 
					customer.getAccounts().get(0).getAccountType().equals("Savings")) {
				
				accountCreatable = true;
			}
			else{
				for(int i=0; i<2; i++) {
					String accountType = customer.getAccounts().get(i).getAccountType(); 
					if(accountType.equals("Current") || accountType.equals("Savings")) {
						accountCreatable = true;
						continue;
					}
					else {
						accountCreatable = false;
						break;
					}
				}
				if(!customer.getAccounts().get(0).equals(customer.getAccounts().get(1)) && accountCreatable) {
					//fine
				}
				else {
					accountCreatable = false;
				}
			}
			if(accountCreatable) {
				//validation
				if(customer.getEmailId().contains("@gmail.com") && customer.getMobileNumber().length() == 13
						&& customer.getMobileNumber().startsWith("+91") && customer.getAadharNo()!=0) {
					logger.info("account has been created for " + customer.getName());
					return customerRepo.save(customer).getCustomerId();
				
				}
			}
			else{
				throw new BadCustomerCreationRequestException("Account can't be create...");
			}
		}
		else{
			if(customerInDB.get().getAccounts().size() == 1 && customer.getAccounts().size() == 1) {
				if(!customerInDB.get().getAccounts().get(0).getAccountType().equals(customer.getAccounts().get(0).getAccountType())) {
					String accountType = customer.getAccounts().get(0).getAccountType(); 
					if(accountType.equals("Current") || accountType.equals("Savings")) {
						if(customer.getEmailId().endsWith("@gmail.com") && customer.getMobileNumber().length() == 13
								&& customer.getMobileNumber().startsWith("+91") && customer.getAadharNo()!=0) {
							customerInDB.get().getAccounts().add(customer.getAccounts().get(0)); //update existing acc.
							return customerRepo.save(customerInDB.get()).getCustomerId();
						
						}
					}
				}
				
			}
			else {
				throw new BadCustomerCreationRequestException("Account can't create...");
			}
		}
		throw new BadCustomerCreationRequestException("Account can't create...");
	}
	
	
	//edit customer personal details
	public void UpdateCustomerPersonalDetails(CustomerNewDetails customerNewDetails) throws CustomerNotFoundException, InvalidJsonException{
		if(customerNewDetails.getEmailId().endsWith("@gmail.com") && customerNewDetails.getMobileNumber().length() == 13
				&& customerNewDetails.getMobileNumber().startsWith("+91") && customerNewDetails.getAadharNo()!=0) {
			Optional<CustomerDetails>customer = customerRepo.findByAadharNo(customerNewDetails.getAadharNo());
			customer.ifPresentOrElse(cus -> customerRepo.save(cus), 
					()-> {
//						//Lambda expressions do not allow throwing checked exceptions directly without handling them.
//						throw new CustomerNotFoundException("CustomerNotAvailable in DB"); // wrong way
						throw new RuntimeException(new CustomerNotFoundException("CustomerNotAvailable in DB")); 
					});
		}
		else throw new InvalidJsonException("wrong data passed");
	}
	
	//Able to fetch one or more customer personal details including account details too
	public CustomerDetails fetchById(int customerId) throws CustomerNotFoundException{
		Optional<CustomerDetails> customer = customerRepo.findById(customerId);
		if(customer.isPresent()) {
			return customer.get();
		}
		else throw new CustomerNotFoundException("CustomerNotAvailable in DB");
	}

	public List<CustomerDetails> fetchAllCustomer(){
		return customerRepo.findAll();
	}
	
	
//	4. Customers can transfer money from one account to another account if sufficient amount is available in senders account
	
	public void transferMoney(MoneyTransfer moneyTransfer) throws CustomerNotFoundException, InsufficientFundException{
		if(moneyTransfer.getSenderAccountId() != moneyTransfer.getReceiverAccountId()) {//different acc
			Optional<AccountDetails> sender = accountRepo.findById(moneyTransfer.getSenderAccountId());
			Optional<AccountDetails> receiver = accountRepo.findById(moneyTransfer.getReceiverAccountId());
			if(sender.isPresent() && receiver.isPresent()) {
				if(sender.get().getAmountAvailable() > moneyTransfer.getAmount()) {
					receiver.get().setAmountAvailable(
							receiver.get().getAmountAvailable() + moneyTransfer.getAmount()
							); //money credited to receiver
					sender.get().setAmountAvailable(
							sender.get().getAmountAvailable() - moneyTransfer.getAmount()
							); //money deducted from sender
					accountRepo.save(sender.get());
					accountRepo.save(receiver.get());
				}
				else throw new InsufficientFundException("Insufficient balance... First deposit some amount :)");
			}
			else throw new CustomerNotFoundException("Provided Bank account details is invalid");
		}
		
	}
	
	//add amount
	public void deposit(int accountId, double amount) throws CustomerNotFoundException {
		Optional<AccountDetails>account = accountRepo.findById(accountId);
		if(account.isPresent()) {
			account.get().setAmountAvailable(account.get().getAmountAvailable()+amount);
			accountRepo.save(account.get());
			logger.info("Hi there... rs" + amount + "/- has deposited to your bank account");
		}
		else throw new CustomerNotFoundException("Provided Bank account details is invalid");
	}
	
}




































