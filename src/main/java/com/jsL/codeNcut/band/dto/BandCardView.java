package com.jsL.codeNcut.band.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BandCardView {

	private int bandId;
	private String title;
	private String place;
	private String part;
	private String description;
	private double lat;
	private double lng;
	
}
