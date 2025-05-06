package com.jsL.codeNcut.user;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.jsL.codeNcut.user.domain.User;
import com.jsL.codeNcut.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserRestController {
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
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
			session.setAttribute("nickname", user.getNickname());
			session.setAttribute("loginType", user.getLoginType());
			resultMap.put("result", "success");
		
			String redisKey = "user:" + user.getId(); // Redis에 저장할 고유 키
            redisTemplate.opsForHash().put(redisKey, "userId", user.getId());
            redisTemplate.opsForHash().put(redisKey, "userLoginId", user.getLoginId());
            redisTemplate.opsForHash().put(redisKey, "nickname", user.getNickname());
            redisTemplate.opsForHash().put(redisKey, "loginType", user.getLoginType());

            // 세션 정보 Redis에 저장 후 TTL 설정 (예: 30분)
            redisTemplate.expire(redisKey, 30, TimeUnit.MINUTES);
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
	
	
	@PostMapping("/confirm")
	public Map<String, String>confirmUser(
			@RequestParam String loginId
			,@RequestParam String password
			,HttpSession session
			){
		int userId = (Integer)session.getAttribute("userId");
		boolean result = userService.userConfirm(userId, loginId, password);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	@PostMapping("/change")
	public Map<String, String> changeUser(
			@RequestParam String loginId
			,@RequestParam String password
			,@RequestParam String phoneNumber
			,@RequestParam String nickname
			,@RequestParam String email
			,HttpSession session
			){
		int userId = (Integer)session.getAttribute("userId");
		boolean result = userService.updateUser(userId, loginId, password, phoneNumber, nickname, email);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	@PostMapping("/deleteConfirm")
	public Map<String, String>deleteConfirm(@RequestParam String loginId
			,@RequestParam String password
			,HttpSession session){
		int userId = (Integer)session.getAttribute("userId");
		boolean result = userService.userConfirm(userId, loginId, password);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	

	
	
	
	
	@GetMapping("/deleteUser")
	public RedirectView withdraw(HttpSession session) {
		int userId = (Integer)session.getAttribute("userId");
		
		boolean result = userService.withdrawUser(userId);
		
		
		if(result) {
			return new RedirectView("/user/login-view");
		}
		return new RedirectView("/user/fail");
	}
	
	
	@PostMapping("/kakaoLogin")
	public Map<String, String>kakaoLogin(
			@RequestParam long kakaoId
			,HttpSession session
			){
		User user = userService.getUserByKakaoId(kakaoId);
		Map<String, String> resultMap  = new HashMap<>();
		if(user != null) {
			session.setAttribute("userId", user.getId());
			session.setAttribute("loginType", user.getLoginType());
			session.setAttribute("nickname", user.getNickname());
			resultMap.put("result", "success");
			
			String redisKey = "user:" + user.getId(); // Redis에 저장할 고유 키
            redisTemplate.opsForHash().put(redisKey, "userId", user.getId());
            redisTemplate.opsForHash().put(redisKey, "nickname", user.getNickname());
            redisTemplate.opsForHash().put(redisKey, "loginType", user.getLoginType());

            // 세션 정보 Redis에 저장 후 TTL 설정 (예: 30분)
            redisTemplate.expire(redisKey, 30, TimeUnit.MINUTES);
		}
		else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	
	
	@PostMapping("/kakaoJoin")
	public Map<String, String> kakaoJoin(
			@RequestParam long kakaoId,
			@RequestParam String phoneNumber,
			@RequestParam String nickname,
			@RequestParam String email
			){
		boolean result = userService.addUserByKakao(kakaoId, phoneNumber, nickname, email);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	@PostMapping("/kakaoChange")
	public Map<String, String> kakaoChange(
			@RequestParam long kakaoId,
			@RequestParam String phoneNumber,
			@RequestParam String nickname,
			@RequestParam String email
			){
		boolean result = userService.updateKakaoUser(kakaoId, phoneNumber, nickname, email);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	@DeleteMapping("/kakaoDelete")
	public Map<String, String>kakaoDelete(
			@RequestParam long kakaoId
			){
		boolean result = userService.withdrawKakaoUser(kakaoId);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	
	
	
	
}
