package com.jsL.codeNcut.akbo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
			HttpSession session
			,Model model
			) {
		int userId = (Integer)session.getAttribute("userId");
		List<AkboCardView>akboCardList = akboService.getAkboList(userId);
		
		model.addAttribute("akboCardList", akboCardList);
		
		return "/akbo/akbo";
	}
	
	@GetMapping("/newAkbo-view")
	public String newAkbo() {
		return "akbo/upload";
	}
	
	

}
