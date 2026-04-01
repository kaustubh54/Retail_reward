package com.retail.rewards.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.retail.rewards.dto.MonthlyRewardDTO;
import com.retail.rewards.dto.RewardResponseDTO;
import com.retail.rewards.entity.Transaction;
import com.retail.rewards.exception.ResourceNotFoundException;
import com.retail.rewards.repository.TransactionRepository;
import com.retail.rewards.service.impl.RewardServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit test using Mockito for RewardServiceImpl
 */
@ExtendWith(MockitoExtension.class)
class RewardServiceImplTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private RewardServiceImpl rewardService;

    private Transaction t1;
    private Transaction t2;

    @BeforeEach
    void setup() {
        t1 = new Transaction();
        t1.setCustomerId(101L);
        t1.setAmount(120.0); // 90 points
        t1.setTransactionDate(LocalDate.of(2026, 1, 10));

        t2 = new Transaction();
        t2.setCustomerId(101L);
        t2.setAmount(80.0); // 30 points
        t2.setTransactionDate(LocalDate.of(2026, 1, 15));
    }

    /**
     * ✅ Positive test case
     */
    @Test
    void testGetRewardsByCustomer_Success() {

        List<Transaction> transactions = Arrays.asList(t1, t2);

        when(repository.findByCustomerIdAndTransactionDateBetween(
                eq(101L), any(), any()))
                .thenReturn(transactions);

        RewardResponseDTO response = rewardService.getRewardsByCustomer(101L);

        assertNotNull(response);
        assertEquals(101L, response.getCustomerId());
        assertEquals(120, response.getTotalPoints());

        List<MonthlyRewardDTO> monthly = response.getMonthlyRewards();
        assertEquals(1, monthly.size());
        assertEquals("Jan", monthly.get(0).getMonth());
        assertEquals(120, monthly.get(0).getPoints());

        verify(repository, times(1))
                .findByCustomerIdAndTransactionDateBetween(eq(101L), any(), any());
    }

    /**
     * ✅ Negative test case
     */
    @Test
    void testGetRewardsByCustomer_NoTransactions() {

        when(repository.findByCustomerIdAndTransactionDateBetween(
                eq(101L), any(), any()))
                .thenReturn(Collections.emptyList());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> rewardService.getRewardsByCustomer(101L)
        );

        assertTrue(exception.getMessage().contains("No transactions"));

        verify(repository, times(1))
                .findByCustomerIdAndTransactionDateBetween(eq(101L), any(), any());
    }

    /**
     * ✅ Multiple months scenario
     */
    @Test
    void testGetRewardsByCustomer_MultipleMonths() {

        Transaction feb = new Transaction();
        feb.setCustomerId(101L);
        feb.setAmount(200.0); // 250 points
        feb.setTransactionDate(LocalDate.of(2026, 2, 10));

        List<Transaction> transactions = Arrays.asList(t1, feb);

        when(repository.findByCustomerIdAndTransactionDateBetween(
                eq(101L), any(), any()))
                .thenReturn(transactions);

        RewardResponseDTO response = rewardService.getRewardsByCustomer(101L);

        assertEquals(2, response.getMonthlyRewards().size());
        assertTrue(response.getTotalPoints() > 0);

        verify(repository)
                .findByCustomerIdAndTransactionDateBetween(eq(101L), any(), any());
    }

    /**
     * ✅ Edge case: No reward points (<50)
     */
    @Test
    void testGetRewardsByCustomer_NoRewardPoints() {

        Transaction low = new Transaction();
        low.setCustomerId(101L);
        low.setAmount(40.0); // 0 points
        low.setTransactionDate(LocalDate.of(2026, 1, 10));

        when(repository.findByCustomerIdAndTransactionDateBetween(
                eq(101L), any(), any()))
                .thenReturn(List.of(low));

        RewardResponseDTO response = rewardService.getRewardsByCustomer(101L);

        assertEquals(0, response.getTotalPoints());
    }
}