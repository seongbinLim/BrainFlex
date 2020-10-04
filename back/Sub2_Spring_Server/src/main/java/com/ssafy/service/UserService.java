package com.ssafy.service;

import java.util.List;

import com.ssafy.dto.UserDto;

public interface UserService {

	public Integer login(String email, String pw);

	public void update(UserDto userDto);

	public void insert(UserDto userDto);

	public void delete(String id);

	public List<UserDto> selectAll();
	
	public int chkid(String id);

	public UserDto select(String email);

}
