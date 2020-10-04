package com.ssafy.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.dto.ProblemDto;
import com.ssafy.service.ProblemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"6.Recommend"})
@CrossOrigin(origins = "*")
@ControllerAdvice
@Controller 
@RequestMapping("/recommend")
public class RecommendController {

	@Autowired
	private ProblemService problemService;

	@ApiOperation(value = "화면 구성용 문제 3개 가져오기", notes = "문제 테이블 상단 3개 가져옵니다.")
	@RequestMapping(value = "/getproblem", method = RequestMethod.GET)
	private ResponseEntity<HashMap<String, Object>> login()
			throws ServletException, IOException {

		HashMap<String, Object> obj = new HashMap<>();

		List<ProblemDto> list = problemService.searchtest();
		
		if (list.size() <= 0) { // 아이디가 있으면
			obj.put("res", "0"); // 실패
			obj.put("msg", "로그인 실패. \n아이디와 비밀번호를 확인해주세요");

			return new ResponseEntity<HashMap<String, Object>>(obj, HttpStatus.NOT_ACCEPTABLE);
		}
		
		obj.put("res", "1"); // 성공
		obj.put("data", list); // 성공

		return ResponseEntity.status(HttpStatus.OK).body(obj);
	}
		
	@ApiOperation(value = "문제 추천", notes = " 추천 문제들을 리턴합니다")
	@RequestMapping(value = "/getproblems/{id}", method = RequestMethod.GET)
	private ResponseEntity<HashMap<String, Object>> getproblems(@PathVariable String id)
			throws ServletException, IOException, ScriptException {

//		flask for docker and deploy
		String route = "http://flask:5000/recommend/"+id;
				
//		flask for local 
//		String route = "http://localhost:5000/recommend/"+id;

		HashMap<String, Object> return_obj = new HashMap<>();
		List problems = null;
		
		try {
			URL obj = new URL(route);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        //전송방식
	        con.setRequestMethod("GET");

	        //Request Header 정의
	        con.setConnectTimeout(10000);       //컨텍션타임아웃 10초
	        con.setReadTimeout(5000);           //컨텐츠조회 타임아웃 5총

	        int responseCode = con.getResponseCode();
	        System.out.println("\nSending 'GET' request to URL : " + route);
	        System.out.println("Response Code : " + responseCode);

	        Charset charset = Charset.forName("UTF-8");
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	        
	        while ((inputLine = in.readLine()) != null) {
	        	System.out.println(inputLine);
	            response.append(inputLine);
	        }
	        in.close();
	        
	        ScriptEngineManager sem = new ScriptEngineManager(); 
	        ScriptEngine engine = sem.getEngineByName("javascript"); 
	        String json = response.toString(); 
	        String script = "Java.asJSONCompatible(" + json + ")"; 
	        Object result = engine.eval(script); 
	        Map contents = (Map)result;
	        
	        contents.forEach((k,v) ->{
	        	System.out.println(k.toString() + ":" + v.toString());
	        });
	        
	        problems = (List) contents.get("problems");
	        System.out.println(problems);
		} catch (MalformedURLException e) {
			System.out.println(e);
			return_obj.put("res", "0"); // 실패
		} catch (IOException e) {
			System.out.println(e);
			return_obj.put("res", "0"); // 실패
		}
		
		
		

		List<ProblemDto> data = new LinkedList<>();
		
		try {
			for (int i = 0; i < 3; i++) {
				int number = (int) problems.get(i);
				
				//1000보다 작은 문제 뽑히는 문제 임시방편		이거 해결되면 지워야합니다!!!!!!!!!!!!
				if(number < 1000) number +=1000;
				
				ProblemDto problemdto = problemService.select(number);
				data.add(problemdto);
			}
			
			return_obj.put("res", "1"); // 성공
			return_obj.put("data", data); // 성공
			
		} catch (Exception e) {
			System.out.println(e);
			return_obj.put("res", "0"); // 실패
		}
		
		
		return ResponseEntity.status(HttpStatus.OK).body(return_obj);
	}
}
