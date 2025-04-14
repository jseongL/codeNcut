package com.jsL.codeNcut.daily.dto;

import java.util.List;

import com.jsL.codeNcut.daily.comment.dto.CommentView;

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
	
	private List<CommentView>commentList;

 	private boolean isLike;
 	private int likeCount;


}
