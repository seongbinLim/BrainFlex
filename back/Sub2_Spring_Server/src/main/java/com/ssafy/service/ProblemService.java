package com.ssafy.service;

import java.util.List;

import com.ssafy.dto.ProblemDto;

public interface ProblemService {

	public List<ProblemDto> searchtest();
	
	public int getTotalCount(String classitication);
	
	public int getLastPage(String classitication);
	
	public List<ProblemDto> getProblemList(int page, String classitication);

	public ProblemDto select(int number);


}
