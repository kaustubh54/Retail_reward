package com.retail.rewards.dto;

import java.time.LocalDate;

/**
 * Response DTO for transaction
 */
public class TransactionResponseDTO {

	private Long id;
	private Long customerId;
	private Double amount;
	private LocalDate transactionDate;

	public TransactionResponseDTO(Long id, Long customerId, Double amount, LocalDate transactionDate) {
		this.id = id;
		this.customerId = customerId;
		this.amount = amount;
		this.transactionDate = transactionDate;
	}

	public Long getId() {
		return id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public Double getAmount() {
		return amount;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

}
