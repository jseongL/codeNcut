package com.jsL.codeNcut.user.service;

import org.springframework.stereotype.Service;

import com.jsL.codeNcut.user.common.Encrypt;
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
		
		//String encyptPassword = MD5HashingEncoder.encode(password);
		
		String saltedPassword = Encrypt.main(password);//회원가입할 때 입력받은 비밀번호
		
		User user = User.builder()
				.loginId(loginId)
				.password(saltedPassword)
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
		
	public User getUser(String loginId, String password) {//로그인할 때 입력받은 비밀번호
		//String encyptPassword = MD5HashingEncoder.encode(password);
		String saltedPassword = Encrypt.main(password);//수행과정에서 비밀번호 바뀜
		
		
		
		User user = userRepository.findByLoginIdAndPassword(loginId, saltedPassword);	
		return user;	
	}
	
	
	public boolean isDuplicateByLoginId(String loginId) {
		int count = userRepository.countByLoginId(loginId);
		if(count == 0) {
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
}
