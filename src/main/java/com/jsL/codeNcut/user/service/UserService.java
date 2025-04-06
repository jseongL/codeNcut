package com.jsL.codeNcut.user.service;

import org.springframework.stereotype.Service;

import com.jsL.codeNcut.user.common.MD5HashingEncoder;
import com.jsL.codeNcut.user.domain.User;
import com.jsL.codeNcut.user.repository.UserRepository;

import jakarta.persistence.PersistenceException;

@Service
public class UserService {

	private final UserRepository userRepository;
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public boolean addUser(String loginId, String password, String phoneNumber, String nickname, String email) {
		
		String encyptPassword = MD5HashingEncoder.encode(password);
		
		User user = User.builder()
				.loginId(loginId)
				.password(encyptPassword)
				.phoneNumber(phoneNumber)
				.nickname(nickname)
				.email(email)
				.build();
		
		try {
			userRepository.save(user);
		}catch(PersistenceException e) {
			return false;
		}
		
		return true;
	}
		
	public User getUser(String loginId, String password) {	
		String encyptPassword = MD5HashingEncoder.encode(password);
		User user = userRepository.findByLoginIdAndPassword(loginId, encyptPassword);	
		return user;	
	}
	
	
	public boolean isDuplicateByLoginId(String loginId) {
		int count = userRepository.countByLoginId(loginId);
		if(count == 0) {
			return false;
		}
		return true;
	}
	
	
	public User getUserByUserId(int userId) {
		User user = userRepository.findById(userId);
		return user;
	}
	
	
	
	
	
	
}
