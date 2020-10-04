package com.ssafy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dao.UserDao;
import com.ssafy.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	@Override
	public Integer login(String email, String pw) {
		return dao.login(email, pw);
	}

	@Override
	public void insert(UserDto userDto) {
		dao.insert(userDto);
	}

	@Override
	public void update(UserDto userDto) {
		dao.update(userDto);
	}

	@Override
	public void delete(String id) {
		dao.delete(id);
	}

	@Override
	public List<UserDto> selectAll() {
		return dao.selectAll();
	}

	@Override
	public int chkid(String id) {
		return dao.chkid(id);
	}

	@Override
	public UserDto select(String email) {
		return dao.select(email);
	}
}
