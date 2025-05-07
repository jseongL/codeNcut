package com.jsL.codeNcut.daily.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsL.codeNcut.daily.like.domain.DailyLike;

import jakarta.transaction.Transactional;

public interface DailyLikeRepository extends JpaRepository<DailyLike, Integer>{

	void deleteByUserId(int userId);
	public int countByDailyId(int dailyId);
	public boolean existsByDailyIdAndUserId(int dailyId, int userId);
	@Transactional
	public void deleteByDailyIdAndUserId(int dailyId,  int userId);
}
