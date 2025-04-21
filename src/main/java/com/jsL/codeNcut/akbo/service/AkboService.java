package com.jsL.codeNcut.akbo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsL.codeNcut.akbo.domain.Akbo;
import com.jsL.codeNcut.akbo.dto.AkboCardView;
import com.jsL.codeNcut.akbo.repository.AkboRepository;
import com.jsL.codeNcut.config.FileManager;

import jakarta.persistence.PersistenceException;

@Service
public class AkboService {
	private final AkboRepository akboRepository;
	public AkboService(AkboRepository akboRepository) {
		this.akboRepository = akboRepository;
	}

	public boolean addAkbo(int userId, String songName, String artist, MultipartFile file) {
		String urlPath = FileManager.saveFile(userId, file);
			
		Akbo akbo = Akbo.builder()
				.userId(userId)
				.songName(songName)
				.artist(artist)
				.imgPath(urlPath)
				.build();
		try {
			akboRepository.save(akbo);
		}catch(PersistenceException e) {
			return false;
		}
		return true;
	}
	
	public List<AkboCardView>getAkboList(){
		List<Akbo>akboList = akboRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		
		List<AkboCardView>akboCardList = new ArrayList<>();
		
		for(Akbo akbo:akboList) {
			AkboCardView akboCardView = AkboCardView.builder()
					.akboId(akbo.getId())
					.songName(akbo.getSongName())
					.artist(akbo.getArtist())
					.imgPath(akbo.getImgPath())
					.build();
			
			akboCardList.add(akboCardView);
		}
		return akboCardList;
	}
	
	
	public List<AkboCardView> getMyAkboList(int userId){
		List<Akbo> myAkboList = akboRepository.findByUserId(userId);
		List<AkboCardView> myAkboCardList = new ArrayList<>();
		
		for(Akbo akbo:myAkboList) {
			
			AkboCardView myAkboCardView = AkboCardView.builder()
					.akboId(akbo.getId())
					.songName(akbo.getSongName())
					.artist(akbo.getArtist())
					.imgPath(akbo.getImgPath())
					.build();
			myAkboCardList.add(myAkboCardView);	
					
		}
		return myAkboCardList;
	}
	
	public Akbo getAkbo(int id) {
		Optional<Akbo> optionalAkbo = akboRepository.findById(id);
		return optionalAkbo.orElse(null);
	}
	
	
	
	public boolean updateAkbo(int akboId, int userId, String songName, String artist, MultipartFile file) {
		Optional<Akbo> optionalAkbo = akboRepository.findById(akboId);
		String urlPath = FileManager.saveFile(userId, file);
		
		if(optionalAkbo.isPresent()) {
			
			Akbo akbo = optionalAkbo.get();
			akbo = akbo.toBuilder()
					.songName(songName)
					.artist(artist)
					.imgPath(urlPath)
					.build();
			try {
				akboRepository.save(akbo);
			}catch(PersistenceException e) {
				return false;
			}
		}
		else {
			return false;
		}
		return true;
	}
	
	
	public boolean deleteAkbo(int akboId) {
		Optional<Akbo> optionalAkbo = akboRepository.findById(akboId);
		
		if(optionalAkbo.isPresent()) {
			
			Akbo akbo  = optionalAkbo.get();
			
			try {
				akboRepository.delete(akbo);
			}catch(PersistenceException e) {
				return false;
			}
		}
		else {
			return false;
		}
		return true;
	}
	
	
	public List<AkboCardView> searchAkbo(String text){
		List<Akbo> akboList = akboRepository.findBySongNameContainingOrArtistContaining(text, text);
		List<AkboCardView> akboSearchCardList = new ArrayList<>();
		for(Akbo akbo:akboList) {
			AkboCardView akboCardView = AkboCardView.builder()
					.akboId(akbo.getId())
					.songName(akbo.getSongName())
					.artist(akbo.getArtist())
					.imgPath(akbo.getImgPath())
					.build();
			akboSearchCardList.add(akboCardView);
		}
		return akboSearchCardList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
