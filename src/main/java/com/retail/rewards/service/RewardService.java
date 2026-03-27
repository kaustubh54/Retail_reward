package com.retail.rewards.service;

import com.retail.rewards.dto.RewardResponseDTO;
import com.retail.rewards.dto.TransactionResponseDTO;
import com.retail.rewards.entity.Transaction;

public interface RewardService {
	RewardResponseDTO getRewardsByCustomer(Long customerId);
	Transaction addTransaction(Transaction transaction);
}
