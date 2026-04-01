package com.retail.rewards.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.retail.rewards.dto.MonthlyRewardDTO;
import com.retail.rewards.dto.RewardResponseDTO;
import com.retail.rewards.entity.Transaction;
import com.retail.rewards.exception.ResourceNotFoundException;
import com.retail.rewards.repository.TransactionRepository;
import com.retail.rewards.service.RewardService;
import com.retail.rewards.util.RewardUtil;

/**
 * Service implementation for calculating customer rewards.
 */
@Service
public class RewardServiceImpl implements RewardService {

    private final TransactionRepository repository;

    /**
     * Constructor injection for repository
     */
    public RewardServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    /**
     * Calculates monthly and total reward points for a customer
     */
    @Override
    public RewardResponseDTO getRewardsByCustomer(Long customerId) {

    	LocalDate now = LocalDate.now();
        LocalDate threeMonthsAgo = now.minusMonths(3);
        List<Transaction> transactions = repository.findByCustomerIdAndTransactionDateBetween(customerId,threeMonthsAgo, now);

        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("No transactions found for customer: " + customerId);
        }

        // Group transactions by month and calculate points
        Map<String, Integer> monthlyPoints = transactions.stream()
                .collect(Collectors.groupingBy(
                        this::getMonthName,
                        Collectors.summingInt(t -> RewardUtil.calculatePoints(t.getAmount()))
                ));

        // Convert map to DTO list
        List<MonthlyRewardDTO> monthlyRewards = monthlyPoints.entrySet().stream()
                .map(entry -> new MonthlyRewardDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // Calculate total points
        int totalPoints = monthlyRewards.stream()
                .mapToInt(MonthlyRewardDTO::getPoints)
                .sum();

        return new RewardResponseDTO(customerId, monthlyRewards, totalPoints);
    }

    /**
     * Helper method to extract month name from transaction date
     */
    private String getMonthName(Transaction transaction) {
        return transaction.getTransactionDate()
                .getMonth()
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }
//    Added sql script to load the data 
//    @Override
//    public Transaction addTransaction(Transaction transaction) {
//
//        return repository.save(transaction);
//    }
    
}