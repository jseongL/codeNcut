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

import jakarta.servlet.http.HttpSession;

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
	
	
	
	@GetMapping("/myBand-view")
	public String myBand(
			HttpSession session
			,Model model
			) {
		int userId = (Integer)session.getAttribute("userId");
		List<BandCardView>myBandCardList = bandService.getMyBandList(userId);
		model.addAttribute("myBandCardList", myBandCardList);
		return "band/myBand";
	}
	
	
	@GetMapping("/modify-view")
	public String modifyBand(
			@RequestParam int id
			,Model model
			) {
		Band band = bandService.getBand(id);
		model.addAttribute("band", band);
		return "band/modify";
	}
	
	
	
	@GetMapping("/map-view")
	public String mapView() {
		return "/band/map";
	}
	
	@GetMapping("/search")
	public String bandSearch(
			@RequestParam String text
			,Model model
			) {
		List<BandCardView> bandCardList = bandService.searchBand(text);
		model.addAttribute("bandCardList", bandCardList);
		return "band/bandSearch";
	}
	
	
	
	
	@GetMapping("/calender-view")
	public String calenderView(
			Model model
			) {//밴드테이블의 날짜, 소개, 세션, 장소 정보 전체 가져오기
		List<BandCardView> bandCalenderList = bandService.getBandForCalender();
		model.addAttribute("bandCalenderList", bandCalenderList);
		return "band/calender";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
