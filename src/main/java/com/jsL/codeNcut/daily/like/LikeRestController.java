package com.jsL.codeNcut.daily.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsL.codeNcut.daily.like.service.DailyLikeService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/daily")
public class LikeRestController {
	private final DailyLikeService dailyLikeService;
	public LikeRestController(DailyLikeService dailyLikeService) {
		this.dailyLikeService = dailyLikeService;
	}
	
	
	@PostMapping("/like")
	public Map<String, String>like(
			@RequestParam int dailyId
			,HttpSession session
			){
		int userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(dailyLikeService.addLike(dailyId, userId)) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	@PostMapping("/dislike")
	public Map<String, String>dislike(
			@RequestParam int dailyId
			,HttpSession session
			){
		int userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> resultMap = new HashMap<>();
		dailyLikeService.deleteLikeByDailyIdAndUserId(dailyId, userId);
		
		resultMap.put("result", "success");
		
		
		return resultMap;
		
	}
	
	
	

}
