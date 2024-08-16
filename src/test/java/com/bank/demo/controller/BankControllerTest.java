package com.bank.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bank.demo.DTO.CustomerNewDetails;
import com.bank.demo.model.AccountDetails;
import com.bank.demo.model.CustomerDetails;
import com.bank.demo.service.BankServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MyController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankControllerTest {
	
	@Autowired 
	MockMvc mockMvc;
	
	/*
	 * Using @MockBean for the Service Layer: Since you're using Spring's WebMvcTest, 
	 * you should use @MockBean to mock the service layer (BankServiceImpl) instead of @Autowired or normal @Mock. 
	 * This will ensure that the BankServiceImpl is correctly injected into the MyController class.
	 */
	@MockBean
	BankServiceImpl bankService;
	
	@InjectMocks
    private MyController myController;
	
	
	public String convertToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
		
	}

	@Test
	public void testAddAccount() throws Exception{
		List<AccountDetails> accounts =new ArrayList<AccountDetails>();
		accounts.add(new AccountDetails(10, "Savings", 500));
		accounts.add(new AccountDetails(11, "Current", 300));
		CustomerDetails customer = new CustomerDetails(101, "+911234567890", "Trina", "trina@gmail.com", 123456,
				accounts);
		
		String inputJson = convertToJson(customer);
		String uri = "/banking/addCustomer";
		
//		when(bankService.addAccount(customer)).thenReturn(customer.getCustomerId()); //will not work
		/*
		 * When you use when(bankService.addAccount(customer)).thenReturn(customer.getCustomerId());, 
		 * Mockito expects an exact match of the customer object that is passed to the addAccount method. 
		 * If the customer object used in the test is not the exact same instance or if its fields are not exactly 
		 * the same (even if they are logically equivalent), the match will fail, 
		 * and the stubbed method won't be invoked as expected.

		   To resolve this, you should use argument matchers with Mockito's Mockito.any() when the exact object 
		   instance might not be available or if the comparison of objects might be problematic due to reference 
		   equality issues. This tells Mockito to accept any instance of CustomerDetails as a valid argument.
		 */
		
		when(bankService.addAccount(Mockito.any(CustomerDetails.class))).thenReturn(customer.getCustomerId());
		assertEquals(101, bankService.addAccount(customer)); 
		
		MvcResult mvcResult = 
			mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andExpect(MockMvcResultMatchers.status().isCreated())// Verify the status code is 201
				.andExpect(MockMvcResultMatchers.content().string("101"))
				.andReturn();
		
		Mockito.verify(bankService, Mockito.atLeastOnce()).addAccount(Mockito.any(CustomerDetails.class));
		
		int statusCode = mvcResult.getResponse().getStatus();// we can verify the status code by this way also
		String returnedCustomerId = mvcResult.getResponse().getContentAsString();
		
		assertEquals(201, statusCode);
		
		assertEquals("101", returnedCustomerId);// we can verify the response body by this way also
		
	}
	/*
	 * 	@PutMapping("/updateCustomer")
	public ResponseEntity updateCustomerDetails(@RequestBody CustomerNewDetails customerNewDetails) throws Exception{
		bankService.UpdateCustomerPersonalDetails(customerNewDetails);
		return new ResponseEntity(HttpStatus.OK);
	}
	 */
	@Test
	public void testUpdateCustomerDetails() throws Exception{
		CustomerNewDetails customerNewDetails = new CustomerNewDetails(); //Null or not null does not matter as we are using "Mockito.any(CustomerNewDetails.class)"
		
		String inputJson = convertToJson(customerNewDetails);
		String uri = "/banking/updateCustomer";
		
		mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.verify(bankService, atLeastOnce()).UpdateCustomerPersonalDetails(Mockito.any(CustomerNewDetails.class));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
