package com.jsL.codeNcut.band.form.dto;

import com.jsL.codeNcut.band.dto.BandCardView;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FormCardWithBandCardView {
	
	private FormCardView formCard;
	private BandCardView bandCard;

}
