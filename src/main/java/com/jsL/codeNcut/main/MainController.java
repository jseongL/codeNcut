package com.jsL.codeNcut.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jsL.codeNcut.user.domain.User;
import com.jsL.codeNcut.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
//	 @Autowired
//	 private RedisTemplate<String, Object> redisTemplate;
	private UserService userService;
	public MainController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/main-view")
	public String mainView(
			HttpSession session
			,Model model
			) {
		
		
//		if (userId != null) {
//            // Redis에서 사용자 정보를 가져옵니다
//            String redisKey = "user:" + userId;
//            Map<Object, Object> userData = redisTemplate.opsForHash().entries(redisKey);
//
//            // Redis에서 가져온 사용자 정보를 모델에 추가합니다
//            model.addAttribute("user", userData);
//        }
		
		
		return "main/main";
	}
	
	
}
