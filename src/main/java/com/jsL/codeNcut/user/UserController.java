package com.jsL.codeNcut.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/join-view")
	public String join() {
		return "user/join";
	}
	
	
	@GetMapping("/login-view")
	public String login() {
		return "user/login";
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session){
		//로그인하고 세션에 저장된 id와 userId 제거
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		return "redirect:/main-view";
		 		//ResponseBody없이 사용가능
	}
	
	
	

}
