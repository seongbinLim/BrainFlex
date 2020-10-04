package com.ssafy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.dto.ProblemDto;

@Repository
public class ProblemDaoImpl implements ProblemDao {

	@Autowired
	SqlSession session;

	@Override
	public List<ProblemDto> searchtest() {
		return session.getMapper(ProblemDao.class).searchtest();
	}

	@Override
	public int selectTotalCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return session.getMapper(ProblemDao.class).selectTotalCount(map);
	}

	@Override
	public List<ProblemDto> selectProblemList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return session.getMapper(ProblemDao.class).selectProblemList(map);
	}

	@Override
	public ProblemDto select(int number) {
		// TODO Auto-generated method stub
		return session.getMapper(ProblemDao.class).select(number);
	}


}
