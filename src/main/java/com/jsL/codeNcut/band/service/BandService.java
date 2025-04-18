package com.jsL.codeNcut.band.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jsL.codeNcut.band.domain.Band;
import com.jsL.codeNcut.band.dto.BandCardView;
import com.jsL.codeNcut.band.repository.BandRepository;

import jakarta.persistence.PersistenceException;

@Service
public class BandService {
	private final BandRepository bandRepository;
	public BandService(BandRepository bandRepository) {
		this.bandRepository = bandRepository;
	}
	
	public boolean addBand(int userId, String title, String place, String part, String description) {
		Band band = Band.builder()
				.userId(userId)
				.title(title)
				.place(place)
				.part(part)
				.description(description)
				.build();
		
		try {
			bandRepository.save(band);
		}catch(PersistenceException e) {
			return false;
		}
		return true;
	}
	
	
	public List<BandCardView>getBandCardList(){
		List<Band>bandList = bandRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		List<BandCardView>bandCardList = new ArrayList<>();
		
		for(Band band:bandList) {
			
			BandCardView bandCardView = BandCardView.builder()
					.bandId(band.getId())
					.title(band.getTitle())
					.place(band.getPlace())
					.part(band.getPart())
					.description(band.getDescription())
					.build();
			bandCardList.add(bandCardView);
		}
		return bandCardList;
	}
	
	
	public Band getBand(int id) {
		Optional<Band> optionalBand = bandRepository.findById(id);
		return optionalBand.orElse(null);
	}
	
	
	
	
	
	
	
}
