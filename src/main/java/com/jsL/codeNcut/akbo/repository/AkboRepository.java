package com.jsL.codeNcut.akbo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsL.codeNcut.akbo.domain.Akbo;

public interface AkboRepository extends JpaRepository<Akbo, Integer>{
	public List<Akbo>findByUserId(int userId);
	public List<Akbo>findBySongNameContainingOrArtistContaining(String text1, String text2);
	void deleteByUserId(int userId);
}
