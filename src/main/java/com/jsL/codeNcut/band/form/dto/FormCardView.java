package com.jsL.codeNcut.band.form.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FormCardView {
	
	private int userId;
	private int bandId;
	private String name;
	private String phoneNumber;
	private String place;
	private String experience;
	private String introduce;
	private String pnp;
	

}
