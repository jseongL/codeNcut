package com.jsL.codeNcut.band.form.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jsL.codeNcut.band.form.domain.Form;
import com.jsL.codeNcut.band.form.dto.FormCardView;
import com.jsL.codeNcut.band.form.repository.FormRepository;

import jakarta.persistence.PersistenceException;

@Service
public class FormService {

	private final FormRepository formRepository;
	public FormService(FormRepository formRepository) {
		this.formRepository = formRepository;
	}
	
	public boolean addForm(int bandId, String phoneNumber, String place, String experience, String introduce) {
		Form form = Form.builder()
				.bandId(bandId)
				.phoneNumber(phoneNumber)
				.place(place)
				.experience(experience)
				.introduce(introduce)
				.build();
		try {
			formRepository.save(form);
		}catch(PersistenceException e) {
			return false;
		}
		return true;
	}
	
	
	public List<FormCardView> getAppliedForm(int id){
		List<Form> formList = formRepository.findByBandId(id);
		List<FormCardView> formCardList = new ArrayList<>();
		for(Form form:formList) {
			FormCardView formCardView = FormCardView.builder()
					.phoneNumber(form.getPhoneNumber())
					.place(form.getPlace())
					.experience(form.getExperience())
					.introduce(form.getIntroduce())
					.build();
			formCardList.add(formCardView);
		}
		return formCardList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
