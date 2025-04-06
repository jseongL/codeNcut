package com.jsL.codeNcut.buy.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsL.codeNcut.buy.domain.Buy;
import com.jsL.codeNcut.buy.repository.BuyRepository;
import com.jsL.codeNcut.config.FileManager;
import com.jsL.codeNcut.user.domain.User;
import com.jsL.codeNcut.user.service.UserService;

import jakarta.persistence.PersistenceException;

@Service
public class BuyService {
	private final BuyRepository buyRepository;
	private final UserService userService;
	public BuyService(BuyRepository buyRepository, UserService userService) {
		this.buyRepository = buyRepository;
		this.userService = userService;
	}
	
	
	public boolean insertBuy(int userId, String description, String model, int buyYear, int price, MultipartFile file, String status) {
		
		String urlPath = FileManager.saveFile(userId, file);
		User user = userService.getUserByUserId(userId);
		
		
		Buy buy = Buy.builder()
				.userId(userId)
				.nickname(user.getNickname())
				.description(description)
				.model(model)
				.buyYear(buyYear)
				.price(price)
				.imgPath(urlPath)
				.status(status)
				.build();
		try {
			buyRepository.save(buy);
		}catch(PersistenceException e) {
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
