package com.ssafy.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.dto.CSCReportDto;

@Repository
public class CSCReportDaoImpl implements CSCReportDao{

	@Autowired
	SqlSession session;

	
	@Override
	public List<CSCReportDto> getreport(String id, int true_false) {
		// TODO Auto-generated method stub
		return session.getMapper(CSCReportDao.class).getreport(id, true_false);
	}


	@Override
	public List<Integer> getproblem_numbers(String id, int true_false) {
		// TODO Auto-generated method stub
		return session.getMapper(CSCReportDao.class).getproblem_numbers(id, true_false);
	}

}
