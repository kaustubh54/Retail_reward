package com.retail.rewards.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.rewards.dto.MonthlyRewardDTO;
import com.retail.rewards.dto.RewardResponseDTO;
import com.retail.rewards.entity.Transaction;
import com.retail.rewards.service.RewardService;
import org.springframework.http.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.LocalDate;
import java.util.Arrays;

/**
 * Unit test for RewardController using Mockito
 */
@ExtendWith(MockitoExtension.class)
class RewardControllerTest {

    @Mock
    private RewardService rewardService;

    @InjectMocks
    private RewardController rewardController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    /**
     * Setup MockMvc
     */
    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(rewardController).build();
        objectMapper = new ObjectMapper();
    }

    /**
     *  Positive Test Case
     */
    @Test
    void testGetRewards_Success() throws Exception {

        setup();

        RewardResponseDTO response = new RewardResponseDTO(
                101L,
                Arrays.asList(new MonthlyRewardDTO("Jan", 120)),
                120
        );

        when(rewardService.getRewardsByCustomer(101L)).thenReturn(response);

        mockMvc.perform(get("/api/rewards/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(101))
                .andExpect(jsonPath("$.totalPoints").value(120))
                .andExpect(jsonPath("$.monthlyRewards[0].month").value("Jan"))
                .andExpect(jsonPath("$.monthlyRewards[0].points").value(120));
    }

    /**
     *  Negative Test Case (Exception)
     */
    @Test
    void testGetRewards_Exception() throws Exception {

        setup();

        when(rewardService.getRewardsByCustomer(999L))
                .thenThrow(new RuntimeException("No transactions found"));

        mockMvc.perform(get("/api/rewards/999"))
                .andExpect(status().isInternalServerError());
    }


    /**
     *  Positive Test Case
     */
    @Test
    void testAddTransaction_Success() throws Exception {

        Transaction request = new Transaction();
        request.setCustomerId(101L);
        request.setAmount(150.0);
        request.setTransactionDate(LocalDate.of(2026, 3, 27));

        Transaction saved = new Transaction();
        saved.setId(1L);
        saved.setCustomerId(101L);
        saved.setAmount(150.0);
        saved.setTransactionDate(LocalDate.of(2026, 3, 27));

        when(rewardService.addTransaction(request)).thenReturn(saved);

        mockMvc.perform(post("/api/rewards/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value(101))
                .andExpect(jsonPath("$.amount").value(150.0));
    }

    /**
     *  Negative Test Case (Exception)
     */
    @Test
    void testAddTransaction_Exception() throws Exception {

        Transaction request = new Transaction();
        request.setCustomerId(101L);
        request.setAmount(150.0);
        request.setTransactionDate(LocalDate.of(2026, 3, 27));

        when(rewardService.addTransaction(request))
                .thenThrow(new RuntimeException("Error saving transaction"));

        mockMvc.perform(post("/api/rewards/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}
    
