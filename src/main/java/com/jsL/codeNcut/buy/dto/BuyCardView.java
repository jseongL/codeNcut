package com.jsL.codeNcut.buy.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BuyCardView {
	

	private int buyId;
	private String nickname;
	private String description;
	private String model;
	private int buyYear;
	private int price;
	private String imgPath;
	private String status;

}
