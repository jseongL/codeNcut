package com.jsL.codeNcut.band;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsL.codeNcut.band.domain.Band;
import com.jsL.codeNcut.band.dto.BandCardView;
import com.jsL.codeNcut.band.service.BandService;

@Controller
@RequestMapping("/band")
public class BandController {
	private final BandService bandService;
	public BandController(BandService bandService) {
		this.bandService = bandService;
	}

	@GetMapping("/band-view")
	public String bandView(
			Model model
			) {
		List<BandCardView> bandCardList = bandService.getBandCardList();
		
		model.addAttribute("bandCardList", bandCardList);
		
		return "band/band";
	}
	
	
	@GetMapping("/newBand")
	public String newBand() {
		return "band/upload";
	}
	
	@GetMapping("/bandMore")
	public String bandMore(
			@RequestParam int id
			,Model model
			) {
		
		Band moreBand = bandService.getBand(id);
		model.addAttribute("moreBand", moreBand);
		return "band/bandMore";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
