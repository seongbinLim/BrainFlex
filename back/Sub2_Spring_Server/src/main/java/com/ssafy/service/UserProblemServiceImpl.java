package com.ssafy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dao.UserProblemDao;
import com.ssafy.dto.UserProblemDto;

@Service
public class UserProblemServiceImpl implements UserProblemService{

	@Autowired
	private UserProblemDao dao;
	
	@Override
	public void update(UserProblemDto item) {
		// TODO Auto-generated method stub
		dao.update(item);
	}

	@Override
	public void insert(UserProblemDto item) {
		// TODO Auto-generated method stub
		dao.insert(item);
	}

}
