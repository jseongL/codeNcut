package com.jsL.codeNcut.band.form;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsL.codeNcut.band.form.service.FormService;

@RestController
@RequestMapping("/band")
public class FormRestController {

	private final FormService formService;
	public FormRestController(FormService formService) {
		this.formService = formService;
	}
	
	
	
	
	@PostMapping("/createForm")
	public Map<String, String> formCreate(
			@RequestParam int bandId
			,@RequestParam String phoneNumber
			,@RequestParam String place
			,@RequestParam String experience
			,@RequestParam String introduce
			){
		boolean result = formService.addForm(bandId, phoneNumber, place, experience, introduce);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
}
