package com.jsL.codeNcut.daily;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jsL.codeNcut.daily.dto.DailyCardView;
import com.jsL.codeNcut.daily.service.DailyService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/daily")
public class DailyController {
	
	private final DailyService dailyService;
	public DailyController(DailyService dailyService) {
		this.dailyService = dailyService;
	}
	
	@GetMapping("/daily-view")
	public String dailyView(
			Model model
			,HttpSession session
			) {
		int userId = (Integer)session.getAttribute("userId");
		List<DailyCardView>dailyCardList = dailyService.getDailyList(userId);
		
		model.addAttribute("dailyCardList", dailyCardList);
		
		return "daily/daily";
	}
	
	@GetMapping("/newDaily-view")
	public String newDaily() {
		return "daily/upload";
	}

}
