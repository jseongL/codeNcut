package com.jsL.codeNcut.daily.like.service;

import org.springframework.stereotype.Service;

import com.jsL.codeNcut.daily.like.domain.DailyLike;
import com.jsL.codeNcut.daily.like.repository.DailyLikeRepository;

import jakarta.persistence.PersistenceException;

@Service
public class DailyLikeService {
	private final DailyLikeRepository dailyLikeRepository;
	public DailyLikeService(DailyLikeRepository dailyLikeRepository) {
		this.dailyLikeRepository = dailyLikeRepository;
	}

	public boolean addLike(int dailyId, int userId) {
			DailyLike dailyLike = DailyLike.builder()
				.dailyId(dailyId)
				.userId(userId)
				.build();//값 넣기
				
				try {
				dailyLikeRepository.save(dailyLike);
				}catch(PersistenceException e) {
					return false;
				}
			return true;
	}
	
	public int getLikeCount(int dailyId) {
 		return dailyLikeRepository.countByDailyId(dailyId);
 	}
	
	
	
	
	public boolean isLikeByDailyIdAndUserId(int dailyId, int userId){
		return dailyLikeRepository.existsByDailyIdAndUserId(dailyId, userId);
	}
	
	
	public void deleteLikeByDailyIdAndUserId(int dailyId, int userId){//검증할 필요없이 삭제해도됨 
		dailyLikeRepository.deleteByDailyIdAndUserId(dailyId, userId);
	}
	
	
}
