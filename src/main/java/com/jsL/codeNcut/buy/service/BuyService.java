package com.jsL.codeNcut.buy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsL.codeNcut.buy.domain.Buy;
import com.jsL.codeNcut.buy.dto.BuyCardView;
import com.jsL.codeNcut.buy.repository.BuyRepository;
//import com.jsL.codeNcut.buy.repository.BuySearchRepository;
import com.jsL.codeNcut.config.FileManager;
import com.jsL.codeNcut.user.domain.User;
import com.jsL.codeNcut.user.service.UserService;

import jakarta.persistence.PersistenceException;

@Service
public class BuyService {
	private final BuyRepository buyRepository;
	private final UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(BuyService.class);
	//private final BuySearchRepository buySearchRepository;
	public BuyService(BuyRepository buyRepository, UserService userService) {
		this.buyRepository = buyRepository;
		this.userService = userService;
		//this.buySearchRepository = buySearchRepository;
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
	        buy = buyRepository.save(buy);  // ID 생성됨

//	        // Elasticsearch에 저장
//	        BuyDocument document = BuyDocument.builder()
//	                .id(buy.getId())
//	                .userId(buy.getUserId())
//	                .model(buy.getModel())
//	                .description(buy.getDescription())
//	                .build();
//	        buySearchRepository.save(document);

	    } catch (PersistenceException e) {
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

	    if (optionalBuy.isPresent()) {
	        Buy buy = optionalBuy.get();

	        buy = buy.toBuilder()
	                .description(description)
	                .model(model)
	                .buyYear(buyYear)
	                .price(price)
	                .imgPath(urlPath)
	                .build();

	        try {
	            buyRepository.save(buy);

//	            // Elasticsearch에도 업데이트
//	            BuyDocument doc = BuyDocument.builder()
//	                    .id(buy.getId())
//	                    .userId(buy.getUserId())
//	                    .description(buy.getDescription())
//	                    .model(buy.getModel())
//	                    .build();
//
//	            buySearchRepository.save(doc);
	        } catch (PersistenceException e) {
	            return false;
	        }
	    } else {
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
				//buySearchRepository.deleteById(buy.getId());//es에서 삭제
			}catch(PersistenceException e) {
				return false;
			}
			
		}else {
			return false;
		}
		
		return true;
		
	}
	
	
	public boolean modifyStatus(int buyId) {
		Optional<Buy>optionalBuy = buyRepository.findById(buyId);
		
		if(optionalBuy.isPresent()) {
			Buy buy = optionalBuy.get();
			buy = buy.toBuilder()
					.status("판매완료")
					.build();
			try {
				buyRepository.save(buy);
			}catch(PersistenceException e) {
				return false;
			}
		}
		else {
			return false;
		}
		return true;
	}
	
	//jpa like속성 으로 조회
	public List<BuyCardView> searchBuy(String text) {
		long start = System.currentTimeMillis(); // 시작 시간
		List<Buy>buyList = buyRepository.findByModelContainingOrDescriptionContaining(text, text);
		List<BuyCardView> buyCardList = new ArrayList<>();
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
		
		 long end = System.currentTimeMillis(); // 종료 시간
		 logger.info("JPA LIKE 검색 시간: {}ms", (end - start)); 
		return buyCardList;
	}
	
	
	
//	//elastic search로 조회
//	public List<BuyCardView> searchBuy(String text) {
//	    long start = System.currentTimeMillis(); // 시작 시간
//
//	    List<BuyDocument> documents = buySearchRepository.search(text);
//	    List<BuyCardView> result = new ArrayList<>();
//
//	    for (BuyDocument doc : documents) {
//	        Buy buy = buyRepository.findById(doc.getId()).orElse(null);
//	        if (buy == null) continue;
//
//	        User user = userService.getUserByUserId(buy.getUserId());
//
//	        BuyCardView view = BuyCardView.builder()
//	            .buyId(buy.getId())
//	            .nickname(user.getNickname())
//	            .description(buy.getDescription())
//	            .model(buy.getModel())
//	            .buyYear(buy.getBuyYear())
//	            .price(buy.getPrice())
//	            .imgPath(buy.getImgPath())
//	            .status(buy.getStatus())
//	            .build();
//	        result.add(view);
//	    }
//
//	    long end = System.currentTimeMillis(); // 종료 시간
//	    logger.info("Elastic Search 검색 시간: {}ms", (end - start)); 
//
//	    return result;
//	}
	
	
	
	
	
	
	
	
	
	

}
