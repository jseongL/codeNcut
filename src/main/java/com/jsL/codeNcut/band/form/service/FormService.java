package com.jsL.codeNcut.band.form.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jsL.codeNcut.band.dto.BandCardView;
import com.jsL.codeNcut.band.form.domain.Form;
import com.jsL.codeNcut.band.form.dto.FormCardView;
import com.jsL.codeNcut.band.form.dto.FormCardWithBandCardView;
import com.jsL.codeNcut.band.form.repository.FormRepository;

import jakarta.persistence.PersistenceException;

@Service
public class FormService {

	private final FormRepository formRepository;
	public FormService(FormRepository formRepository) {
		this.formRepository = formRepository;
	}
	
	public boolean addForm(int userId, int bandId, String name, String phoneNumber, String place, String experience, String introduce) {
		Form form = Form.builder()
				.userId(userId)
				.bandId(bandId)
				.name(name)
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
					.userId(form.getUserId())
					.bandId(form.getBandId())
					.name(form.getName())
					.phoneNumber(form.getPhoneNumber())
					.place(form.getPlace())
					.experience(form.getExperience())
					.introduce(form.getIntroduce())
					.pnp(form.getPnp())
					.build();
			formCardList.add(formCardView);
		}
		return formCardList;
	}
	
	
	public List<FormCardView> getMyFormList(int userId) {
		List<Form> formList = formRepository.findByUserId(userId);
		List<FormCardView> myFormCardList = new ArrayList<>();
		for(Form form:formList) {
			FormCardView formCardview = FormCardView.builder()
					.bandId(form.getBandId())
					.phoneNumber(form.getPhoneNumber())
					.place(form.getPlace())
					.experience(form.getExperience())
					.introduce(form.getIntroduce())
					.pnp(form.getPnp())
					.build();
			myFormCardList.add(formCardview);
		}
		return myFormCardList;
	}
	
	
	public boolean updatePnp(int userId, int bandId, String pnp) {
		Form form = formRepository.findByUserIdAndBandId(userId, bandId);
		Form updatedForm = form.toBuilder()
			.pnp(pnp)
			.build();
		try {
			formRepository.save(updatedForm);
		}catch(PersistenceException e) {
			return false;
		}
		return true;
	}
	
	
	public boolean updateFrom(int bandId, String name, String phoneNumber, String place, String experience, String introduce) {
		Form form= formRepository.findFirstByBandId(bandId);
		Form updateForm = form.toBuilder()
				.name(name)
				.phoneNumber(phoneNumber)
				.place(place)
				.experience(experience)
				.introduce(introduce)
				.build();
		try {
			formRepository.save(updateForm);
		}catch(PersistenceException e) {
			return false;
		}
		return true;
	}
	
	
	public boolean deleteForm(int bandId) {
		Form form = formRepository.findFirstByBandId(bandId);
		try {
			formRepository.delete(form);
		}catch(PersistenceException e) {
			return false;
		}
		return true;
	}
	
	//컨트롤러의 이중포문 서비스로 옮김
	public List<FormCardWithBandCardView>getFormCardWithBandCardList(List<FormCardView> myFormCardList, List<BandCardView> bandCardList){
		List<FormCardWithBandCardView>FormCardWithBandCardList = new ArrayList<>();	
		for (FormCardView form : myFormCardList) {
		        for (BandCardView band : bandCardList) {
		            if (form.getBandId() == band.getBandId()) {
		            	FormCardWithBandCardList.add(
		                		FormCardWithBandCardView.builder()
		                		.formCard(form)
		                		.bandCard(band)
		                		.build()
		                		);
		                break;
		            }
		        }
		    }
		return FormCardWithBandCardList;
	}
	
	
	
	
	
	
	
	
	
	
	
}
