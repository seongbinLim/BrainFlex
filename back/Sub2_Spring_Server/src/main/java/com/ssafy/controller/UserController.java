package com.ssafy.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssafy.dto.UserDto;
import com.ssafy.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"1.User"})
@CrossOrigin(origins = "*")
@ControllerAdvice
@Controller 
@RequestMapping("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;


	@ApiOperation(value = "로그인", notes = "로그인 합니다")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	private ResponseEntity<HashMap<String, Object>> login(@RequestBody UserDto userDto)
			throws ServletException, IOException {
		
		System.out.println("email: " + userDto.getEmail()); 
		System.out.println("pw: " + userDto.getPw());

		HashMap<String, Object> obj = new HashMap<>();
		
		// id 확인 
		Integer check = userService.login(userDto.getEmail(), userDto.getPw()); // 아이디 확인

		if (check == null || check == 0) {
			obj.put("res", "0"); // 실패
			obj.put("msg", "로그인 실패. \n아이디와 비밀번호를 확인해주세요");

			return new ResponseEntity<HashMap<String, Object>>(obj, HttpStatus.NOT_ACCEPTABLE);
		}
		UserDto data = userService.select(userDto.getEmail());
		
		obj.put("res", "1"); // 성공 
		obj.put("data", data); // 성공 
		
 
		return ResponseEntity.status(HttpStatus.OK).body(obj);
	}
	
	
	@ApiOperation(value = "회원가입", notes = "회원 가입을 합니다")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	private ResponseEntity<HashMap<String, String>> insert(@RequestBody UserDto userDto)
			throws ServletException, IOException {
		
		System.out.println("insert ");
		System.out.println("email " + userDto.getEmail());
		System.out.println("id " + userDto.getId());
		System.out.println("pw " + userDto.getPw());
		
		HashMap<String, String> obj = new HashMap<>();

		// id 확인
		int check = userService.chkid(userDto.getId()); // 아이디 확인

		if (check == 1) { // 아이디가 있으면
			obj.put("res_insert_userDto", "0"); // 실패
			obj.put("msg", "아이디가 있습니다.\n중복된 아이디입니다.\n아이디를 확인해주세요");

			return new ResponseEntity<HashMap<String, String>>(obj, HttpStatus.NOT_ACCEPTABLE);
		}
		userService.insert(userDto);

		obj.put("res_insert_userDto", "1"); // 성공
		
		return ResponseEntity.status(HttpStatus.OK).body(obj);
	}

	@ApiOperation(value = "회원 정보 조회", notes = "회원의 정보를 조회 리턴합니다")
	@RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
	private ResponseEntity<HashMap<String, Object>> select(@PathVariable String id)
			throws ServletException, IOException {

		HashMap<String, Object> obj = new HashMap<>();
		if (id == null) {
			obj.put("res", "0"); // 실패
			obj.put("msg", "죄송합니다.\n 아이디를 확인해주세요.");

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(obj);
		}
		UserDto userinfo = userService.select(id);

		obj.put("res", "1"); // 성공
		obj.put("userinfo", userinfo);

		return ResponseEntity.status(HttpStatus.OK).body(obj);
	}

	@ApiOperation(value = "회원 정보 수정", notes = "회원의 정보를 수정합니다")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	private ResponseEntity<HashMap<String, String>> update(@RequestBody UserDto userDto)
			throws ServletException, IOException {

		userService.update(userDto);

		HashMap<String, String> obj = new HashMap<>();
		obj.put("res", "1"); // 성공

		return ResponseEntity.status(HttpStatus.OK).body(obj);
	}

	@ApiOperation(value = "회원 삭제", notes = "회원 정보를 삭제합니다")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	private ResponseEntity<HashMap<String, String>> delete(HttpSession session) throws ServletException, IOException {

		HashMap<String, String> obj = new HashMap<>();

		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		userService.delete(userDto.getId());
		session.invalidate();

		obj.put("res", "1"); // 성공

		return ResponseEntity.status(HttpStatus.OK).body(obj);
	}


	
}
