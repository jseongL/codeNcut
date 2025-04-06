package com.jsL.codeNcut.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsL.codeNcut.user.domain.User;
import com.jsL.codeNcut.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	private final UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	
	@PostMapping("/user/join")
	public Map<String, String> join(
			@RequestParam String loginId
			,@RequestParam String password
			,@RequestParam String phoneNumber
			,@RequestParam String nickname
			,@RequestParam String email
			){
		
		boolean result = userService.addUser(loginId, password, phoneNumber, nickname, email);
		
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	
	@PostMapping("/login")
	public Map<String, String>login(
			@RequestParam String loginId
			,@RequestParam String password
			,HttpSession session
			){
		
		User user= userService.getUser(loginId, password);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(user != null) {
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userNickname", user.getNickname());
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	@GetMapping("/duplicate-id")
	public Map<String, Boolean>isDuplicate(@RequestParam String loginId){
		
		boolean result = userService.isDuplicateByLoginId(loginId);
		
		Map<String, Boolean> resultMap = new HashMap<>();
		
		if(result) {
			resultMap.put("result", true);
		}
		else {
			resultMap.put("result", false);
		}
		return resultMap;
	}
	
	
	
	
	
	
	
	
}
