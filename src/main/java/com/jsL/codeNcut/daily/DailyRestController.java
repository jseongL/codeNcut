package com.jsL.codeNcut.daily;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsL.codeNcut.daily.service.DailyService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/daily")
public class DailyRestController {
	
	private final DailyService dailyService;
	public DailyRestController(DailyService dailyService) {
		this.dailyService = dailyService;
	}
	
	@PostMapping("/createDaily")
	public Map<String, String>createDaily(
			@RequestParam String title
			,@RequestParam String contents
			,@RequestParam MultipartFile imgPath
			,HttpSession session
			){
		
		int userId = (Integer)session.getAttribute("userId");
		
		boolean result = dailyService.insertDaily(userId, title, contents, imgPath);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}

	
	@PostMapping("/updateDaily")
	public Map<String, String>updateDaily(
			@RequestParam int dailyId
			,@RequestParam String title
			,@RequestParam String contents
			,@RequestParam MultipartFile imgPath 
			,HttpSession session
			){
		int userId = (Integer)session.getAttribute("userId");
		boolean result = dailyService.updateDaily(dailyId, userId, title, contents, imgPath);
		
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	@DeleteMapping("/deleteProfile")
	public Map<String, String>withDrawDaily(
			@RequestParam int dailyId
			){
		
		boolean result = dailyService.deleteDaily(dailyId);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	
	
	
	
	
}
