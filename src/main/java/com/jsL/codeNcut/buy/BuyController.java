package com.jsL.codeNcut.buy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/buy")
public class BuyController {
	
	@GetMapping("/buy-view")
	public String buyView(
			Model model
			) {
		
		
		
		return "buy/buy";
	}
	
	
	@GetMapping("/newBuy-view")
	public String newBuy() {
		return "buy/upload";
	}

}
