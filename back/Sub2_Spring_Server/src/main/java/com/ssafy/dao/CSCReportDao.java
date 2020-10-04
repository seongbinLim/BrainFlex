package com.ssafy.dao;

import java.util.List;

import com.ssafy.dto.CSCReportDto;

public interface CSCReportDao {

	public List<CSCReportDto> getreport(String id, int true_false);

	public List<Integer> getproblem_numbers(String id, int true_false);

}
