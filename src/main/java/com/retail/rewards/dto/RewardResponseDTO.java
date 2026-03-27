package com.retail.rewards.dto;

import java.util.List;

public class RewardResponseDTO {

	private Long customerId;
	private List<MonthlyRewardDTO> monthlyRewards;
	private int totalPoints;

	public RewardResponseDTO(Long customerId, List<MonthlyRewardDTO> monthlyRewards, int totalPoints) {
		this.customerId = customerId;
		this.monthlyRewards = monthlyRewards;
		this.totalPoints = totalPoints;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public List<MonthlyRewardDTO> getMonthlyRewards() {
		return monthlyRewards;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

}
