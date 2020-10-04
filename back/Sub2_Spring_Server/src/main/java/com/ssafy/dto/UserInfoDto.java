package com.ssafy.dto;

public class UserInfoDto {

	private String id;
	private int score;
	
	public UserInfoDto(String id, int score) {
		super();
		this.id = id;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
}