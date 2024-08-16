package com.bank.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.demo.model.AccountDetails;

@Repository
public interface AccountRepository extends JpaRepository<AccountDetails, Integer> {
	
//	@Query("SELECT a.CustomerDetails.customerId FROM AccountDetails a WHERE a.accountId = :accountId")
//    Integer findCustomerIdByAccountId(int accountId);

}
