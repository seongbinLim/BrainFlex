package com.ssafy.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.dto.UserProblemDto;

@Repository
public class UserProblemDaoImpl implements UserProblemDao{

	@Autowired
	SqlSession session;
	
	@Override
	public void insert(UserProblemDto item) {
		// TODO Auto-generated method stub
		session.getMapper(UserProblemDao.class).insert(item);
	}

	@Override
	public void update(UserProblemDto item) {
		// TODO Auto-generated method stub
		session.getMapper(UserProblemDao.class).update(item);
	}

}
