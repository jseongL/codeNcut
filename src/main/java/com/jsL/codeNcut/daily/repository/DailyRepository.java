package com.jsL.codeNcut.daily.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsL.codeNcut.daily.domain.Daily;

public interface DailyRepository extends JpaRepository<Daily, Integer>{
	public List<Daily>findByUserId(int userId);
	void deleteAllByUserId(int userId);
}
