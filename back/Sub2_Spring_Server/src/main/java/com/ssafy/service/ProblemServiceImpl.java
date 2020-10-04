package com.ssafy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dao.ProblemDao;
import com.ssafy.dto.ProblemDto;
import com.ssafy.utill.PageHelper;

@Service
public class ProblemServiceImpl implements ProblemService{
	private final int LINE_PER_PAGE = 10;


	@Autowired
	private ProblemDao problemDao;
	
	@Override
	public List<ProblemDto> searchtest() {
		// TODO Auto-generated method stub
		return problemDao.searchtest();
	}
	
	@Override
	public int getTotalCount(String classitication) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("classitication", classitication);
		return problemDao.selectTotalCount(map);
	}

	@Override
	public int getLastPage(String classitication) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("classitication", classitication);
		return (int)(Math.ceil((double)problemDao.selectTotalCount(map)/LINE_PER_PAGE));
	}

	@Override
	public List<ProblemDto> getProblemList(int page, String classitication) {
		// TODO Auto-generated method stub
		PageHelper pageHelper = new PageHelper(page,LINE_PER_PAGE);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageHelper", pageHelper);
        map.put("classitication", classitication);
        return problemDao.selectProblemList(map);
	}

	@Override
	public ProblemDto select(int number) {
		// TODO Auto-generated method stub
		return problemDao.select(number);
	}

}
