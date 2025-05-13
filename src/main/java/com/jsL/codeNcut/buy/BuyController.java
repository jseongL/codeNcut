package com.jsL.codeNcut.buy;
 
 import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsL.codeNcut.buy.domain.Buy;
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
 	
 	
 	@GetMapping("/myBuy-view")
 	public String myBuy(Model model, HttpSession session) {
 		
 		int userId =(Integer)session.getAttribute("userId");
 	 	
 		List<BuyCardView>myBuyCardList = buyService.getMyBuyList(userId);
 		
 		model.addAttribute("myBuyCardList", myBuyCardList);
 				
 		return "buy/myBuy";
 	}
 	
 	
 	@GetMapping("/modify-view")
 	public String modify(
 			@RequestParam int id
 			,HttpSession session
 			,Model model
 			) {
 		
 		Buy buy = buyService.getBuy(id);
 		
 		model.addAttribute("buy", buy);
 		
 		return "/buy/modify";
 	}
 	
 	
 	
 	
 	@GetMapping("/search")
	public String buySearch(
			@RequestParam String text
			,Model model
			){
 		
		List<BuyCardView> buyCardList  = buyService.searchBuy(text);
		
		model.addAttribute("buyCardList", buyCardList);
		return "buy/buySearch";
	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 
 }