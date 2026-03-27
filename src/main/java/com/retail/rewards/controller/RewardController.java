package com.retail.rewards.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.rewards.dto.RewardResponseDTO;
import com.retail.rewards.entity.Transaction;
import com.retail.rewards.service.RewardService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

	private final RewardService rewardService;

	public RewardController(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	/**
	 * Retrieves reward points for a given customer.
	 *
	 * <p>This API calculates reward points based on customer transactions
	 * and returns both monthly and total reward points.</p>
	 */
	@Operation(summary = "Get rewards by customer ID")
	@GetMapping("/{customerId}")
	public ResponseEntity<RewardResponseDTO> getRewards(@PathVariable Long customerId) {
	    return ResponseEntity.ok(rewardService.getRewardsByCustomer(customerId));
	}
	
	
	/**
	 * Creates a new transaction for a customer.
	 *
	 * <p>This API accepts transaction details in JSON format,
	 * saves the transaction in the database, and returns the saved entity.</p>
	 */
	@Operation(summary = "Post transaction of customers")
	@PostMapping("/transactions")
	public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {

	    Transaction saved = rewardService.addTransaction(transaction);

	    return ResponseEntity.status(201).body(saved);
	}
}

