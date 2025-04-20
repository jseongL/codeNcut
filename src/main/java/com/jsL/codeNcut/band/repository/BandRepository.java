package com.jsL.codeNcut.band.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsL.codeNcut.band.domain.Band;

public interface BandRepository extends JpaRepository<Band, Integer>{
	public List<Band>findByUserId(int userId);
}
