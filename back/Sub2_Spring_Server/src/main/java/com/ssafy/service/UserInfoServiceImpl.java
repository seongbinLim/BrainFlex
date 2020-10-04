package com.ssafy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dao.UserInfoDao;
import com.ssafy.dto.UserInfoDto;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDao dao;

	
}