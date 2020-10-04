package com.ssafy.dto;

public class UserProblemDto {
	private String id;
	private int number;
	private int result;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public UserProblemDto(String id, int number, int result) {
		super();
		this.id = id;
		this.number = number;
		this.result = result;
	}
	public UserProblemDto() {
		super();
	}
	
	
	
}
