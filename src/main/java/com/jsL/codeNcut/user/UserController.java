package com.jsL.codeNcut.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	 @Autowired
	 private RedisTemplate<String, Object> redisTemplate;

	
	@GetMapping("/join-view")
	public String join() {
		return "user/join";
	}
	
	
	@GetMapping("/login-view")
	public String login() {
		return "user/login";
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    // 세션에서 사용자 정보 제거
	    Integer userId = (Integer) session.getAttribute("userId");
	    if (userId != null) {
	        // Redis에서 사용자 정보 삭제
	        String redisKey = "user:" + userId;
	        redisTemplate.delete(redisKey);
	    }

	    // 세션에서 사용자 정보 제거
	    session.removeAttribute("userId");
	    session.removeAttribute("userLoginId");
	    session.removeAttribute("nickname");
	    session.removeAttribute("loginType");

	    // 로그아웃 후 메인 페이지로 리디렉션
	    return "redirect:/main-view";
	}
	
	
	@GetMapping("/confirm-view")
	public String confirmView() {
		return "user/confirm";
	}
	
	
	@GetMapping("/change-view")
	public String changView() {
		return "user/change";
	}
	
	
	@GetMapping("/deleteConfirm-view")
	public String deleteConfirmView() {
		return "user/deleteConfirm";
	}
	
	
	@GetMapping("/kakaoJoin-view")
	public String kakaoJoinView() {
		return "user/kakaoJoin";
	}
	
	

}
