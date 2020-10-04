package com.ssafy.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.service.EmailService;
import com.ssafy.service.EmailServiceImpl;

import io.swagger.annotations.Api;


//이부분 필요하면  obj.put("res", "1"); 이 형식으로 반환가능

@Api(tags = {"5.mail"})
@CrossOrigin(origins = "*")
@ControllerAdvice
@Controller
@RequestMapping("/mail")
public class MailController {
	
	@Autowired
	EmailService service;
	
	@PostMapping("/send")
	@RequestMapping(value = "/send/{email}", method = RequestMethod.GET)
	public ResponseEntity<HashMap<String, String>> emailConfirm(@PathVariable String email) throws Exception{
		HashMap<String, String> obj = new HashMap<>();
		
		EmailServiceImpl.ePw = EmailServiceImpl.createKey();
		System.out.println("전달 받은 이메일 : "+email);
		String code = service.sendSimpleMessage(email);
		
		obj.put("code", code); // 성공

		return ResponseEntity.status(HttpStatus.OK).body(obj);
		
	}
	@PostMapping("/verifyCode")
	@ResponseBody
	public int verifyCode(String code) {
		int result = 0;
		System.out.println("code : "+code);
		System.out.println("code match : "+ EmailServiceImpl.ePw.equals(code));
		if(EmailServiceImpl.ePw.equals(code)) {
			result =1;
		}
		
		return result;
	}
}
