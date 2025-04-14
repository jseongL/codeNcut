package com.jsL.codeNcut.daily.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentView {
	
	private int userId;
	private int commentId;
	private String contents;
	private String nickname;

}
