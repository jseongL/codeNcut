package com.jsL.codeNcut.band.form;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsL.codeNcut.band.form.dto.FormCardView;
import com.jsL.codeNcut.band.form.service.FormService;

@Controller
@RequestMapping("/band")
public class FormController {
	
	private final FormService formService;
	public FormController(FormService formService) {
		this.formService = formService;
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
	
	
	
	
	
	
	

}
