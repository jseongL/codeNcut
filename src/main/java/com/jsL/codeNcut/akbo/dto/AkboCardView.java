package com.jsL.codeNcut.akbo.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AkboCardView {
	
	private int akboId;
	private String songName;
	private String artist;
	private String imgPath;

}
