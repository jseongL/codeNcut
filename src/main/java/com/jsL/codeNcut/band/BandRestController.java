package com.jsL.codeNcut.band;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsL.codeNcut.band.service.BandService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/band")
public class BandRestController {
	
	public final BandService bandService;
	public BandRestController(BandService bandService) {
		this.bandService = bandService;
	}
	
	@PostMapping("/createBand")
	public Map<String, String> createBand(
			@RequestParam String title
			,@RequestParam String place
			,@RequestParam String part
			,@RequestParam String description
			,HttpSession session
			){
		int userId = (Integer)session.getAttribute("userId");
		boolean result = bandService.addBand(userId, title, place, part, description);
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
