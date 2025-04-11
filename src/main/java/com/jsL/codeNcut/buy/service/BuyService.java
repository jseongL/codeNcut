package com.jsL.codeNcut.buy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	
	public List<BuyCardView>getMyBuyList(int userId){//내 악기구매 게시물 리스트 가져오기
		List<Buy>myBuyList = buyRepository.findByUserId(userId);
		
		List<BuyCardView>buyCardList = new ArrayList<>();
		for(Buy buy:myBuyList) {
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
	
	
	public Buy getBuy(int id){
		Optional<Buy>optionalBuy = buyRepository.findById(id);
		
		return optionalBuy.orElse(null);
	}
	
	public boolean updateBuy(int buyId, int userId, String description, String model, int buyYear, int price, MultipartFile file) {
		Optional<Buy> optionalBuy = buyRepository.findById(buyId);
		
		String urlPath = FileManager.saveFile(userId, file);
		
		if(optionalBuy.isPresent()) {
			
			Buy buy = optionalBuy.get();
			
			buy = buy.toBuilder()
					.description(description)
					.model(model)
					.buyYear(buyYear)
					.price(price)
					.imgPath(urlPath)
					.build();
			
			
			try {
				buyRepository.save(buy);//포스트 객체가 파라메터로 전달
			}catch(PersistenceException e){
				return false;
			}
			
		}else {
			return false;
		}
		return true;
		
	}
	
	
	public boolean deleteBuy(int buyId) {
		Optional<Buy> optionalBuy = buyRepository.findById(buyId);
		
		if(optionalBuy.isPresent()) {
			
			Buy buy = optionalBuy.get();
			
			try {
				buyRepository.delete(buy);
			}catch(PersistenceException e) {
				return false;
			}
			
		}else {
			return false;
		}
		
		return true;
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
