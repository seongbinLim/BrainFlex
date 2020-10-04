package com.ssafy.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.dto.ProblemDto;

public interface ProblemDao {

	public List<ProblemDto> searchtest();

	public int selectTotalCount(Map<String, Object> map);
	
	public List<ProblemDto> selectProblemList(Map<String, Object> map);

	public ProblemDto select(int number);
	
}
