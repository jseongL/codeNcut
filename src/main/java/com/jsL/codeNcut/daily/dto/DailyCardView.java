package com.jsL.codeNcut.daily.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DailyCardView {
	
	private int dailyId;
	private String title;
	private String contents;
	private String imgPath;
	private String nickname;
	
	

 	private boolean isLike;
 	private int likeCount;


}
