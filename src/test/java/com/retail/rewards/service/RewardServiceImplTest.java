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
     *  Positive test case
     */
    @Test
    void testGetRewardsByCustomer_Success() {

        List<Transaction> transactions = Arrays.asList(t1, t2);

        // Mock behavior
        when(repository.findByCustomerId(101L)).thenReturn(transactions);

        // Call service
        RewardResponseDTO response = rewardService.getRewardsByCustomer(101L);

        // Assertions
        assertNotNull(response);
        assertEquals(101L, response.getCustomerId());
        assertEquals(120, response.getTotalPoints());

        List<MonthlyRewardDTO> monthly = response.getMonthlyRewards();
        assertEquals(1, monthly.size());
        assertEquals("Jan", monthly.get(0).getMonth());
        assertEquals(120, monthly.get(0).getPoints());

        // Verify interaction
        verify(repository, times(1)).findByCustomerId(101L);
    }

    /**
     *  Negative test case
     */
    @Test
    void testGetRewardsByCustomer_NoTransactions() {

        when(repository.findByCustomerId(101L))
                .thenReturn(Collections.emptyList());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                rewardService.getRewardsByCustomer(101L)
        );

        assertEquals("No transactions found for customer: 101", exception.getMessage());

        verify(repository, times(1)).findByCustomerId(101L);
    }

    /**
     *  Multiple months scenario
     */
    @Test
    void testGetRewardsByCustomer_MultipleMonths() {

        Transaction feb = new Transaction();
        feb.setCustomerId(101L);
        feb.setAmount(200.0); // 250 points
        feb.setTransactionDate(LocalDate.of(2026, 2, 10));

        List<Transaction> transactions = Arrays.asList(t1, feb);

        when(repository.findByCustomerId(101L)).thenReturn(transactions);

        RewardResponseDTO response = rewardService.getRewardsByCustomer(101L);

        assertEquals(2, response.getMonthlyRewards().size());
        assertTrue(response.getTotalPoints() > 0);

        verify(repository).findByCustomerId(101L);
    }
    @Test
    void testAddTransaction_Success() {

        Transaction input = new Transaction();
        input.setCustomerId(101L);
        input.setAmount(150.0);
        input.setTransactionDate(LocalDate.now());

        Transaction saved = new Transaction();
        saved.setId(1L);
        saved.setCustomerId(101L);
        saved.setAmount(150.0);
        saved.setTransactionDate(LocalDate.now());

        when(repository.save(input)).thenReturn(saved);

        Transaction result = rewardService.addTransaction(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(101L, result.getCustomerId());

        verify(repository, times(1)).save(input);
    }

    /**
     *  Negative Test Case (Exception)
     */
    @Test
    void testAddTransaction_Exception() {

        Transaction input = new Transaction();
        input.setCustomerId(101L);
        input.setAmount(150.0);
        input.setTransactionDate(LocalDate.now());

        when(repository.save(input))
                .thenThrow(new RuntimeException("DB error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rewardService.addTransaction(input);
        });

        assertEquals("DB error", exception.getMessage());

        verify(repository).save(input);
    }
}
