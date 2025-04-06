package com.jsL.codeNcut.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@GetMapping("/main-view")
	public String mainView(
			HttpSession session
			,Model model
			) {
		
		
		
		
		
		return "main/main";
	}
	
	
}
