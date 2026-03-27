package com.retail.rewards.dto;

public class MonthlyRewardDTO {
	private String month;
	private int points;

	public MonthlyRewardDTO(String month, int points) {
		this.month = month;
		this.points = points;
	}

	public String getMonth() {
		return month;
	}

	public int getPoints() {
		return points;
	}

}
