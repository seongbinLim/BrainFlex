package com.ssafy.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.CSCReportDto;
import com.ssafy.dto.UserProblemDto;
import com.ssafy.service.CSCReportService;
import com.ssafy.service.UserProblemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"2.Userinfo"})
@CrossOrigin(origins = "*")
@ControllerAdvice
@RestController
@RequestMapping("/userinfo")
public class UserInfoController {

	@Autowired
	UserProblemService userproblemservice;
	@Autowired
	CSCReportService cscreportservice;
	
	@ApiOperation(value = "report", notes = "유저의 분석 정보들을 리턴합니다")
	@RequestMapping(value = "/getreport/{id}", method = RequestMethod.GET)
	private ResponseEntity<HashMap<String, Object>> getreport(@PathVariable String id)
			throws ServletException, IOException, ScriptException {

		HashMap<String, Object> obj = new HashMap<>();
		
		
		try {
			List<Integer> problem_true = cscreportservice.getproblem_numbers(id,1);
			List<Integer> problem_flase = cscreportservice.getproblem_numbers(id,0);
			List<CSCReportDto> csclist_true = cscreportservice.getreport(id,1);
			List<CSCReportDto> csclist_false = cscreportservice.getreport(id,0);

			obj.put("res", "1"); // 성공
			obj.put("problem_true", problem_true);
			obj.put("problem_flase", problem_flase);
			obj.put("classtication_true", csclist_true);
			obj.put("classtication_false", csclist_false);
			
		} catch (Exception e) {
			System.out.println(e);
			obj.put("res", "0"); // 실패
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(obj);
	}
	
	
	
	@ApiOperation(value = "update_userproblem", notes = "update_userproblem")
	@RequestMapping(value = "/update_userproblem/{id}", method = RequestMethod.GET)
	private ResponseEntity<HashMap<String, Object>> update_userproblem(@PathVariable String id)
			throws ServletException, IOException, ScriptException {

//		flask for docker and deploy 
		String route = "http://flask:5000/userdetail/"+id;

//		flask for local 
//		 String route = "http://localhost:5000/userdetail/"+id;
		
		HashMap<String, Object> return_obj = new HashMap<>();
		List<UserProblemDto> list = new ArrayList<>();
		
		try {
			
			URL obj = new URL(route);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	       
	        //전송방식
	        con.setRequestMethod("GET");

	        //Request Header 정의
	        con.setConnectTimeout(10000);       //컨텍션타임아웃 10초
	        con.setReadTimeout(5000);           //컨텐츠조회 타임아웃 5총
	        
	        System.out.println("test1");
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
	        Map<?,?> contents = (Map)result;
	        
	        contents.forEach((k,v) ->{
	        	System.out.println(k.toString() + ":" + v.toString());
	        	String tmp = k.toString();
	        	if(tmp.equals("false")) {
	        		System.out.println("false");
	        		String erage = v.toString().substring(1, v.toString().length()-1).replace(" ","");
	        		System.out.println(erage);
	        		String[] numbers = erage.split(",");
	        		for (int i = 0; i < numbers.length; i++) {
	        			list.add(new UserProblemDto(id, Integer.parseInt(numbers[i]), 0));						
					}
	        	}
	        	else {
	        		System.out.println("true");
	        		String erage = v.toString().substring(1, v.toString().length()-1).replace(" ","");
	        		System.out.println(erage);
	        		String[] numbers = erage.split(",");
	        		for (int i = 0; i < numbers.length; i++) {
	        			list.add(new UserProblemDto(id, Integer.parseInt(numbers[i]), 1));						
					}
	        	}
	        	
	        	list.forEach(item -> userproblemservice.insert(item));
	        	return_obj.put("res", "1"); // 성공
	        });
		} catch (MalformedURLException e) {
			System.out.println(e);
			return_obj.put("res", "0"); // 실패
		} catch (IOException e) {
			System.out.println(e);
			return_obj.put("res", "0"); // 실패
		}

		
		return ResponseEntity.status(HttpStatus.OK).body(return_obj);
	}
	
	
	
	

}
