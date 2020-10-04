package com.ssafy.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.dto.UserDto;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	SqlSession session;

	@Override
	public Integer login(String email, String pw) {
		return session.getMapper(UserDao.class).login(email, pw);
	}

	@Override
	public void insert(UserDto userDto) {
		session.getMapper(UserDao.class).insert(userDto);
	}

	@Override
	public void update(UserDto userDto) {
		session.getMapper(UserDao.class).update(userDto);
	}

	@Override
	public void delete(String id) {
		session.getMapper(UserDao.class).delete(id);
	}

	@Override
	public List<UserDto> selectAll() {
		return session.getMapper(UserDao.class).selectAll();
	}

	@Override
	public int chkid(String id) {
		return session.getMapper(UserDao.class).chkid(id);
	}

	@Override
	public UserDto select(String email) {
		return session.getMapper(UserDao.class).select(email);
	}
}
