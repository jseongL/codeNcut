package com.jsL.codeNcut.buy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsL.codeNcut.buy.domain.Buy;

public interface BuyRepository extends JpaRepository<Buy, Integer>{
	public List<Buy>findByUserId(int userId);
	//public List<Buy> findByModelContainingOrDescriptionContaining(String keyword1, String keyword2);
}
