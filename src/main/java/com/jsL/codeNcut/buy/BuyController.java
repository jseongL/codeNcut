package com.jsL.codeNcut.buy;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jsL.codeNcut.buy.dto.BuyCardView;
import com.jsL.codeNcut.buy.service.BuyService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/buy")
public class BuyController {
	
	private final BuyService buyService;
	public BuyController(BuyService buyService) {
		this.buyService = buyService;
	}
	
	
	
	@GetMapping("/buy-view")
	public String buyView(
			Model model
			
			) {
	
		List<BuyCardView>buyCardList = buyService.getBuyList();
		
		model.addAttribute("buyCardList", buyCardList);
		
		return "buy/buy";
	}
	
	
	@GetMapping("/newBuy-view")
	public String newBuy() {
		return "buy/upload";
	}

}
