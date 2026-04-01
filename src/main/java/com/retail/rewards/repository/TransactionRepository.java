package com.retail.rewards.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.rewards.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByCustomerId(Long customerId);

	List<Transaction> findByTransactionDateBetween(LocalDate start, LocalDate end);
	
	List<Transaction> findByCustomerIdAndTransactionDateBetween(
		    Long customerId, LocalDate startDate, LocalDate endDate);
}
