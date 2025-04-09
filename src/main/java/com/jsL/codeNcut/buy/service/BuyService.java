package com.jsL.codeNcut.buy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsL.codeNcut.buy.domain.Buy;
import com.jsL.codeNcut.buy.dto.BuyCardView;
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
		
		
		Buy buy = Buy.builder()
				.userId(userId)
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
	
	
	public List<BuyCardView>getBuyList(){
		List<Buy>buyList = buyRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		
		List<BuyCardView>buyCardList = new ArrayList<>();
		for(Buy buy:buyList) {
			User user = userService.getUserByUserId(buy.getUserId());
			
			BuyCardView buyCardView = BuyCardView.builder()
					.buyId(buy.getId())
					.nickname(user.getNickname())
					.description(buy.getDescription())
					.model(buy.getModel())
					.buyYear(buy.getBuyYear())
					.price(buy.getPrice())
					.imgPath(buy.getImgPath())
					.status(buy.getStatus())
					.build();
			buyCardList.add(buyCardView);
		}
		return buyCardList;
	}
	
	
	
	
	
	
	
	
	
	

}
