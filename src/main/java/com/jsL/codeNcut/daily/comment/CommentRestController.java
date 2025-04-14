package com.jsL.codeNcut.daily.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsL.codeNcut.daily.comment.service.CommentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/daily")
public class CommentRestController {
	private final CommentService commentService;
	public CommentRestController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/comment")
	public Map<String, String>createComment(
			@RequestParam int dailyId
			,@RequestParam String contents
			,HttpSession session
			){
		int userId = (Integer)session.getAttribute("userId");
		boolean result = commentService.addComment(dailyId, userId, contents);
		Map<String, String> resultMap = new HashMap<>();
		if(result) {
			resultMap.put("result", "success");
		}
		else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	
	
	
	
	
	
	

}
