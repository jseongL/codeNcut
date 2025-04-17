package com.jsL.codeNcut.akbo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsL.codeNcut.akbo.service.AkboService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/akbo")
@RestController
public class AkboRestController {
	
	private final AkboService akboService;
	public AkboRestController(AkboService akboService) {
		this.akboService = akboService;
	}
	
	@PostMapping("/createAkbo")
	public Map<String, String>createAkbo(
			@RequestParam String songName
			,@RequestParam String artist
			,@RequestParam MultipartFile imgPath
			,HttpSession session
			){
		int userId = (Integer)session.getAttribute("userId");
		
		boolean result = akboService.addAkbo(userId, songName, artist, imgPath);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@PostMapping("/updateAkbo")
	public Map<String, String>modifyAkbo(
			@RequestParam int akboId
			,@RequestParam String songName
			,@RequestParam String artist
			,@RequestParam MultipartFile imgPath
			,HttpSession session
			){
		int userId = (Integer)session.getAttribute("userId");
		
		boolean result = akboService.updateAkbo(akboId, userId, songName, artist, imgPath);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	@DeleteMapping("/deleteAkbo")
	public Map<String, String>akboDelete(
			@RequestParam int akboId
			){
		boolean result = akboService.deleteAkbo(akboId);
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
