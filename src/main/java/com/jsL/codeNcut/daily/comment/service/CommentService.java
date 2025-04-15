package com.jsL.codeNcut.daily.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jsL.codeNcut.daily.comment.domain.Comment;
import com.jsL.codeNcut.daily.comment.dto.CommentView;
import com.jsL.codeNcut.daily.comment.repository.CommentRepository;
import com.jsL.codeNcut.daily.domain.Daily;
import com.jsL.codeNcut.user.domain.User;
import com.jsL.codeNcut.user.service.UserService;

import jakarta.persistence.PersistenceException;

@Service
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final UserService userService;
	public CommentService(CommentRepository commentRepository, UserService userService) {
		this.commentRepository = commentRepository;
		this.userService = userService;
	}

	public boolean addComment(int dailyId, int userId, String contetns) {
		Comment comment = Comment.builder()
				.dailyId(dailyId)
				.userId(userId)
				.contents(contetns)
				.build();
		
		try {
			commentRepository.save(comment);
		}catch(PersistenceException e) {
			return false;
		}
		return true;
	}
	

	public List<CommentView>getCommentList(int dailyId){
		List<Comment> commentList = commentRepository.findByDailyId(dailyId);
		List<CommentView>commentViewList = new ArrayList<>();
		
		for(Comment comment:commentList) {
			User user = userService.getUserByUserId(comment.getUserId());
			
			CommentView commentView = CommentView.builder()
					.commentId(comment.getId())
					.userId(comment.getUserId())
					.contents(comment.getContents())
					.nickname(user.getNickname())
					.build();
			commentViewList.add(commentView);
			
		}
		return commentViewList;
	}
	
	
	
	
	public boolean deleteComment(int commentId) {
		Optional<Comment> optionalComment = commentRepository.findById(commentId);
		
		if(optionalComment.isPresent()) {
			Comment comment = optionalComment.get();
			
			try {
				commentRepository.delete(comment);
			}catch(PersistenceException e) {
				return false;
			}
		}else {
			return false;
		}
		return true;
	}
	
	
	
}
