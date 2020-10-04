package com.ssafy.dto;

public class CSCReportDto {
	private String classitication;
	private int count;
	public String getClassitication() {
		return classitication;
	}
	public void setClassitication(String classitication) {
		this.classitication = classitication;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public CSCReportDto(String classitication, int count) {
		super();
		this.classitication = classitication;
		this.count = count;
	}
	public CSCReportDto() {
		super();
	}
	
}
