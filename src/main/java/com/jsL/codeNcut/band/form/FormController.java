package com.jsL.codeNcut.band.form;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsL.codeNcut.band.dto.BandCardView;
import com.jsL.codeNcut.band.form.domain.Form;
import com.jsL.codeNcut.band.form.dto.FormCardView;
import com.jsL.codeNcut.band.form.dto.FormCardWithBandCardView;
import com.jsL.codeNcut.band.form.service.FormService;
import com.jsL.codeNcut.band.service.BandService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/band")
public class FormController {
	
	private final FormService formService;
	private final BandService bandService;
	public FormController(FormService formService, BandService bandService) {
		this.formService = formService;
		this.bandService = bandService;
	}
	
	
	
	@GetMapping("/writeForm-view")
	public String writeBand(
			@RequestParam int id
			,Model model) {
		
		model.addAttribute("id", id);
		
		return "band/writeForm";
	}
	
	
	@GetMapping("/applied-view")
	public String getBandApplied(
			@RequestParam int id     //밴드 아이디
			,Model model
			) {
		List<FormCardView> formCardList = formService.getAppliedForm(id);
		model.addAttribute("formCardList", formCardList);
		return "band/myApplied";
	}
	
	
	
	
	@GetMapping("/myForm-view")
	public String myFormView(HttpSession session, Model model) {
	    int userId = (Integer) session.getAttribute("userId");

	    List<FormCardView> myFormCardList = formService.getMyFormList(userId);
	    List<BandCardView> bandCardList = bandService.getBandCardList();
    
	    // bandId가 일치하는 Form + Band 묶기
	    List<FormCardWithBandCardView> resultList = formService.getFormCardWithBandCardList(myFormCardList, bandCardList);
	    model.addAttribute("formBandList", resultList);
	    return "/band/myForm";
	}

	
	
	
	
	
	
	
	@GetMapping("/modifyForm-view")
	public String modifyForm(
			@RequestParam int id
			,Model model
			) {	
		model.addAttribute("bandId", id);		
		return "band/modifyForm"; 
	}
	
	
	
	

}
