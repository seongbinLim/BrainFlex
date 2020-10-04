package com.ssafy.dto;

public class ProblemDto {

	private Integer number;
	private String link;
	private String level;
	private String title;
	private String classitication;
	private Integer solved;
	private Double avg_attempt;
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getClassitication() {
		return classitication;
	}
	public void setClassitication(String classitication) {
		this.classitication = classitication;
	}
	public Integer getSolved() {
		return solved;
	}
	public void setSolved(Integer solved) {
		this.solved = solved;
	}
	public Double getAvg_attempt() {
		return avg_attempt;
	}
	public void setAvg_attempt(Double avg_attempt) {
		this.avg_attempt = avg_attempt;
	}
	public ProblemDto(Integer number, String link, String level, String title, String classitication, Integer solved,
			Double avg_attempt) {
		super();
		this.number = number;
		this.link = link;
		this.level = level;
		this.title = title;
		this.classitication = classitication;
		this.solved = solved;
		this.avg_attempt = avg_attempt;
	}
	public ProblemDto() {
		super();
	}
	
}
