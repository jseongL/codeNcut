package com.jsL.codeNcut.buy;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsL.codeNcut.buy.service.BuyService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/buy")
@RestController
public class BuyRestController {
	
	private BuyService buyService;
	public BuyRestController(BuyService buyService) {
		this.buyService = buyService;
	}
	
	@PostMapping("/createBuy")
	public Map<String, String>createBuy(
			@RequestParam String description
			,@RequestParam String model
			,@RequestParam int buyYear
			,@RequestParam int price
			,@RequestParam MultipartFile imgPath
			,@RequestParam String status
			,HttpSession session
			){
		
		int userId = (Integer)session.getAttribute("userId");

		
		boolean result = buyService.insertBuy(userId, description, model, buyYear, price, imgPath ,status);
		
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	@PostMapping("/updateProfile")
	public Map<String, String>modify(
			@RequestParam int buyId
			,@RequestParam String description
			,@RequestParam String model
			,@RequestParam int buyYear
			,@RequestParam int price
			,@RequestParam MultipartFile imgPath
			,HttpSession session
			){
		
		int userId = (Integer)session.getAttribute("userId");

		
		boolean result = buyService.updateBuy(buyId, userId, description, model, buyYear, price, imgPath);
		
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
	public Map<String, String>deleteProfile(
			@RequestParam int buyId
			){
		boolean result = buyService.deleteBuy(buyId);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(result) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	@PostMapping("/solded")
	public Map<String, String>solded(
			@RequestParam int buyId
			){
		boolean result = buyService.modifyStatus(buyId);
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
