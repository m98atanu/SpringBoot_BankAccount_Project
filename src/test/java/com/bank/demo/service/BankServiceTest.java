package com.bank.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.demo.DTO.CustomerNewDetails;
import com.bank.demo.exceptionHandler.BadCustomerCreationRequestException;
import com.bank.demo.exceptionHandler.CustomerNotFoundException;
import com.bank.demo.exceptionHandler.InvalidJsonException;
import com.bank.demo.model.AccountDetails;
import com.bank.demo.model.CustomerDetails;
import com.bank.demo.repository.AccountRepository;
import com.bank.demo.repository.CustomerRepository;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class) //You should use @ExtendWith(MockitoExtension.class) or let @RunWith(MockitoJUnitRunner.class) handle Mockito setup.
//@DataJpaTest The import @DataJpaTest is unnecessary for the context of this test since @DataJpaTest is used for JPA tests with real database interaction.
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //@BeforeAll should be static, and you should use @TestInstance(TestInstance.Lifecycle.PER_CLASS) if you want to avoid the static keyword.
public class BankServiceTest {
	
	private static final Logger logger = Logger.getLogger(BankServiceImpl.class.getName());
	
	@Mock
	private CustomerRepository customerRepo;
	
	@Mock
	private AccountRepository accountRepo;
	
	@InjectMocks
	private BankServiceImpl service;
	
	private static CustomerDetails customer;
	
	@BeforeAll
//	public static void setUp() {.
	public void setUp() {
		List<AccountDetails> accounts =new ArrayList<AccountDetails>();
		accounts.add(new AccountDetails(10, "Savings", 500));
		accounts.add(new AccountDetails(11, "Current", 300));
		customer = new CustomerDetails(101, "+911234567890", "Trina", "trina@gmail.com", 123456,
				accounts);

	}

	@Test
	public void testAddAccount() throws BadCustomerCreationRequestException{
		int aadharNo = 123456;
		when(customerRepo.findByAadharNo(aadharNo)).thenReturn(Optional.empty());
		when(customerRepo.save(customer)).thenReturn(customer);
		
		assertEquals(101, service.addAccount(customer));
		
		//let's try with some invalid customer data like, invalid phn no, invalid gmail
		List<AccountDetails> invalidAccounts =new ArrayList<AccountDetails>();
		invalidAccounts.add(new AccountDetails(10, "Savings", 500));
		invalidAccounts.add(new AccountDetails(11, "Current", 300));
		CustomerDetails invalidCustomer = new CustomerDetails(102, "+91123456789089", "Trina", "trina@abc.com", 123456,
				invalidAccounts);
		assertThrows(BadCustomerCreationRequestException.class, ()-> service.addAccount(invalidCustomer));
		
		
		
	}
	
	@Test
	public void testUpdateCustomerPersonalDetails() throws CustomerNotFoundException, InvalidJsonException {
			int aadharNo = 123456;
			when(customerRepo.findByAadharNo(aadharNo)).thenReturn(Optional.of(customer));
			assertDoesNotThrow(()-> service.UpdateCustomerPersonalDetails(
					new CustomerNewDetails("+911234567898", "Trina", "trina@gmail.com", 123456) //mobile no changed
					));
			//let's check with invalid data
			assertThrows(InvalidJsonException.class, ()-> service.UpdateCustomerPersonalDetails(
					new CustomerNewDetails("+911234567898", "Trina", "trina@abc.com", 123456) //invalid mail
					));
	}
	
	@Test
	public void testFetchById() throws CustomerNotFoundException{

		int validCustomerId =101;
		when(customerRepo.findById(validCustomerId)).thenReturn(Optional.ofNullable(customer));
		
		int invalidCustomerId =102;
		
		assertNotNull(service.fetchById(validCustomerId));
		assertThrows(CustomerNotFoundException.class, ()-> service.fetchById(invalidCustomerId));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
