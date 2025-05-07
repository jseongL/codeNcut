package com.jsL.codeNcut.daily.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsL.codeNcut.daily.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	public List<Comment>findByDailyId(int dailyId);
	void deleteByUserId(int userId);
}
