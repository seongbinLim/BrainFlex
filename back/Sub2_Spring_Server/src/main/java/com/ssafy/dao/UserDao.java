package com.ssafy.dao;

import java.util.List;

import com.ssafy.dto.UserDto;

public interface UserDao {

	public Integer login(String email, String pw);

	public void insert(UserDto userDto);

	public void update(UserDto userDto);

	public void delete(String userid);

	public List<UserDto> selectAll();

	public int chkid(String id);

	public UserDto select(String email);

}
