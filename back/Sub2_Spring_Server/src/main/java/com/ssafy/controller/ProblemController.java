package com.ssafy.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssafy.dto.ProblemDto;
import com.ssafy.service.ProblemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = {"7.Problem"})
@CrossOrigin(origins = "*")
@ControllerAdvice
@Controller 
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	
	@ApiOperation(value = "분류별 문제 조회", notes = "분류에 따른 문제들을 조회 리턴합니다")
	@RequestMapping(value = {"/problemList/{page}/", "/problemList/{page}/{classitication}"}, method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> problemList(@PathVariable Integer page, @PathVariable(required = false) String classitication) {

		HashMap<String, Object> obj = new HashMap<>();
		
        List<ProblemDto> userList= problemService.getProblemList(page, classitication);
        System.out.println("classitication : "+classitication);
        
        int lastpage = problemService.getLastPage(classitication);
        int totalcount = problemService.getTotalCount(classitication);
        
		if (userList == null) {
			obj.put("res", "0"); // 실패
			obj.put("msg", "죄송합니다.\n 분류를 확인해주세요.");
	
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(obj);
		}
	
		obj.put("res", "1"); // 성공
		obj.put("data", userList);
		obj.put("lastpage", lastpage);
		obj.put("totalcount", totalcount);
		return ResponseEntity.status(HttpStatus.OK).body(obj);

    }
	
	
	
	
	
	
	
	

		
	
	
	
	
}
