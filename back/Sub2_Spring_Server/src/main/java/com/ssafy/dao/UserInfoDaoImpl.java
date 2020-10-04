package com.ssafy.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.dto.UserInfoDto;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {

	@Autowired
	SqlSession session;

}