package com.jsL.codeNcut.band;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jsL.codeNcut.band.dto.BandCardView;
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
			,@RequestParam Double lat
			,@RequestParam Double lng
			,@RequestParam String date
			,HttpSession session
			){
		int userId = (Integer)session.getAttribute("userId");
		boolean result = bandService.addBand(userId, title, place, part, description, lat, lng, date);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	
	
	@PostMapping("/updateBand")
	public Map<String, String>modifyBand(
			@RequestParam int bandId
			,@RequestParam String title
			,@RequestParam String place
			,@RequestParam String part
			,@RequestParam String description
			,@RequestParam Double lat
			,@RequestParam Double lng
			){
		boolean result = bandService.updateBand(bandId, title, place, part, description, lat, lng);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	@DeleteMapping("/deleteBand")
	public Map<String, String> bandDelete(
			@RequestParam int bandId
			){
		boolean result = bandService.deleteBand(bandId);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	
	
	
	
	
	@GetMapping("/calendar-events")
	@ResponseBody
	public List<BandCardView> getEvents(@RequestParam String start, @RequestParam String end) {
	    return bandService.getBandForCalender(start, end);
	}
	
	
	
	
	
	
	
	
	
	

}
