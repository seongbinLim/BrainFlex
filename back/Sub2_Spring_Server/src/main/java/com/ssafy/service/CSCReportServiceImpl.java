package com.ssafy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dao.CSCReportDao;
import com.ssafy.dto.CSCReportDto;

@Service
public class CSCReportServiceImpl implements CSCReportService{

	@Autowired
	private CSCReportDao dao;
	
	
	@Override
	public List<CSCReportDto> getreport(String id, int true_false) {
		// TODO Auto-generated method stub
		return dao.getreport(id, true_false);
	}


	@Override
	public List<Integer> getproblem_numbers(String id, int true_false) {
		// TODO Auto-generated method stub
		return dao.getproblem_numbers(id, true_false);
	}

}
