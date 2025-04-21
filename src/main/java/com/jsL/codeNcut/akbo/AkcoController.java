package com.jsL.codeNcut.akbo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsL.codeNcut.akbo.domain.Akbo;
import com.jsL.codeNcut.akbo.dto.AkboCardView;
import com.jsL.codeNcut.akbo.service.AkboService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/akbo")
public class AkcoController {
	private final AkboService akboService;
	public AkcoController(AkboService akboService) {
		this.akboService = akboService;
	}
	
	@GetMapping("akbo-view")
	public String akboView(
			Model model
			) {
		
		List<AkboCardView>akboCardList = akboService.getAkboList();
		
		model.addAttribute("akboCardList", akboCardList);
		
		return "/akbo/akbo";
	}
	
	@GetMapping("/newAkbo-view")
	public String newAkbo() {
		return "akbo/upload";
	}
	
	
	@GetMapping("/myAkbo-view")
	public String myAkbo(
			HttpSession session
			,Model model
			) {
		int userId = (Integer)session.getAttribute("userId");
		
		List<AkboCardView> myAkboCardList = akboService.getMyAkboList(userId);
		
		model.addAttribute("myAkboCardList", myAkboCardList);
		
		return "akbo/myAkbo";
	}
	
	@GetMapping("/modify-view")
	public String akboModify(
			@RequestParam int id
			,Model model
			) {
		Akbo akbo = akboService.getAkbo(id);
		model.addAttribute("akbo", akbo);
		return "akbo/modify";
	}
	
	
	@GetMapping("/search")
	public String akboSearch(
			@RequestParam String text
			,Model model
			) {
		List<AkboCardView> akboCardList = akboService.searchAkbo(text);
		model.addAttribute("akboCardList", akboCardList);
		return "akbo/akboSearch";
	}
	
	
	
	
	
	
	
	
	
	

}
