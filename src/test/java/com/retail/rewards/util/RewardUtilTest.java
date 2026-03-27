package com.retail.rewards.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RewardUtilTest {

    @Test
    void testAbove100() {
        assertEquals(90, RewardUtil.calculatePoints(120));
    }

    @Test
    void testBetween50And100() {
        assertEquals(25, RewardUtil.calculatePoints(75));
    }

    @Test
    void testBelow50() {
        assertEquals(0, RewardUtil.calculatePoints(40));
    }

    @Test
    void testExactly100() {
        assertEquals(50, RewardUtil.calculatePoints(100));
    }

    @Test
    void testExactly50() {
        assertEquals(0, RewardUtil.calculatePoints(50));
    }
}
