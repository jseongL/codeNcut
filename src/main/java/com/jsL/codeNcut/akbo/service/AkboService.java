package com.jsL.codeNcut.akbo.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<AkboCardView>getAkboList(int akboId){
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
	
	
	
	
	
	
	
}
