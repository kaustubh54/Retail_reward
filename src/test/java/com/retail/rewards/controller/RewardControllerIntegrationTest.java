package com.retail.rewards.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for RewardController
 * 
 * Loads full Spring context and tests end-to-end flow:
 * Controller → Service → Repository → H2 DB
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration Test for Reward API")
class RewardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     *  Positive Test
     */
    @Test
    @DisplayName("Should return rewards for valid customer")
    void shouldReturnRewardsForValidCustomer() throws Exception {

        mockMvc.perform(get("/api/rewards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.totalPoints").exists())
                .andExpect(jsonPath("$.monthlyRewards").isArray());
    }

    /**
     *  Negative Test
     */
    @Test
    @DisplayName("Should return 404 when customer not found")
    void shouldReturnNotFoundForInvalidCustomer() throws Exception {

        mockMvc.perform(get("/api/rewards/999"))
                .andExpect(status().isNotFound());
    }

    /**
     *  Edge Case: Customer with transactions but zero rewards
     */
    @Test
    @DisplayName("Should handle zero reward scenario")
    void shouldHandleZeroRewards() throws Exception {

        mockMvc.perform(get("/api/rewards/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPoints").exists());
    }
}