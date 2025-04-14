package com.jsL.codeNcut.daily.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsL.codeNcut.buy.domain.Buy;
import com.jsL.codeNcut.config.FileManager;
import com.jsL.codeNcut.daily.comment.dto.CommentView;
import com.jsL.codeNcut.daily.comment.service.CommentService;
import com.jsL.codeNcut.daily.domain.Daily;
import com.jsL.codeNcut.daily.dto.DailyCardView;
import com.jsL.codeNcut.daily.like.service.DailyLikeService;
import com.jsL.codeNcut.daily.repository.DailyRepository;
import com.jsL.codeNcut.user.domain.User;
import com.jsL.codeNcut.user.service.UserService;

import jakarta.persistence.PersistenceException;

@Service
public class DailyService {
	
	private final DailyRepository dailyRepository;
	private final UserService userService;
	private final DailyLikeService dailyLikeService;
	private final CommentService commentService;
	public DailyService(DailyRepository dailyRepository, UserService userService, DailyLikeService dailyLikeService, CommentService commentService) {
		this.dailyRepository = dailyRepository;
		this.userService = userService;
		this.dailyLikeService = dailyLikeService;
		this.commentService = commentService;
	}

	public boolean insertDaily(int userId, String title, String contents, MultipartFile file) {
		String urlPath = FileManager.saveFile(userId, file);
		
		Daily daily = Daily.builder()
				.userId(userId)
				.title(title)
				.contents(contents)
				.imgPath(urlPath)
				.build();
		
		try {
			dailyRepository.save(daily);
		}catch(PersistenceException e) {
			return false;
		}
		
		return true;
	}
	
	
	public List<DailyCardView>getDailyList(int userId){
		List<Daily>dailyList = dailyRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		
		
		List<DailyCardView> dailyCardList = new ArrayList<>();
		
		
		for(Daily daily:dailyList) {
			User user = userService.getUserByUserId(daily.getUserId());
			
			int likeCount = dailyLikeService.getLikeCount(daily.getId());
 			
 			boolean isLike = dailyLikeService.isLikeByDailyIdAndUserId(daily.getId(), userId);
			
			List<CommentView>commentList = commentService.getCommentList(daily.getId());
 			
 			DailyCardView dailyCardView = DailyCardView.builder()
					.dailyId(daily.getId())
					.nickname(user.getNickname())
					.title(daily.getTitle())
					.contents(daily.getContents())
					.imgPath(daily.getImgPath())
					.likeCount(likeCount)
					.isLike(isLike)
					.commentList(commentList)
					.build();
			
			dailyCardList.add(dailyCardView);
		}
		
		return dailyCardList;
		
	}
	
	
	public List<DailyCardView>getMyDailyList(int userId){
		List<Daily>myDailyList = dailyRepository.findByUserId(userId);
		
		List<DailyCardView>myDailyCardList = new ArrayList<>();
		
		for(Daily daily:myDailyList) {
			
			User user = userService.getUserByUserId(daily.getUserId());
			
			int likeCount = dailyLikeService.getLikeCount(daily.getId());
 			
 			boolean isLike = dailyLikeService.isLikeByDailyIdAndUserId(daily.getId(), userId);
			
			List<CommentView>commentList = commentService.getCommentList(daily.getId());
			
			
			DailyCardView dailyCardView = DailyCardView.builder()
					.dailyId(daily.getId())
					.nickname(user.getNickname())
					.title(daily.getTitle())
					.contents(daily.getContents())
					.imgPath(daily.getImgPath())
					.likeCount(likeCount)
					.isLike(isLike)
					.commentList(commentList)
					.build();
			myDailyCardList.add(dailyCardView);
		}
		
		return myDailyCardList;
	}
	
	public Daily getDaily(int id) {
		Optional<Daily>optionalDaily = dailyRepository.findById(id);
		
		return optionalDaily.orElse(null);
	}
	
	
	public boolean updateDaily(int dailyId, int userId, String title, String contents, MultipartFile file) {
		Optional<Daily> optionalDaily = dailyRepository.findById(dailyId);
		
		String urlPath = FileManager.saveFile(userId, file);
		
		if(optionalDaily.isPresent()) {
			
			Daily daily = optionalDaily.get();
			
			daily = daily.toBuilder()
					.title(title)
					.contents(contents)
					.imgPath(urlPath)
					.build();
			
			try {
				dailyRepository.save(daily);
			}catch(PersistenceException e) {
				return false;
			}
			
		}
		else {
			return false;
		}
		return true;
	}
	
	
	
	
	
}
