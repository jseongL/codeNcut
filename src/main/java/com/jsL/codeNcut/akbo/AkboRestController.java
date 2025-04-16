package com.jsL.codeNcut.akbo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	
	
	
	
	

}
