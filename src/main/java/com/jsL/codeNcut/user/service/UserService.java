package com.jsL.codeNcut.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jsL.codeNcut.akbo.repository.AkboRepository;
import com.jsL.codeNcut.band.form.repository.FormRepository;
import com.jsL.codeNcut.band.repository.BandRepository;
import com.jsL.codeNcut.buy.repository.BuyRepository;
import com.jsL.codeNcut.daily.comment.repository.CommentRepository;
import com.jsL.codeNcut.daily.like.repository.DailyLikeRepository;
import com.jsL.codeNcut.daily.repository.DailyRepository;
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
		
		//String saltedPassword = Encrypt.main(password);//회원가입할 때 입력받은 비밀번호
		
		User user = User.builder()
				.loginId(loginId)
				.password(encyptPassword)
				.phoneNumber(phoneNumber)
				.nickname(nickname)
				.email(email)
				.loginType("LOCAL")
				.build();
		
		try {
			userRepository.save(user);
		}catch(PersistenceException e) {
			return false;
		}
		
		return true;
	}
		
	public User getUser(String loginId, String password) {//로그인할 때 입력받은 비밀번호
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
		Optional<User> optionalUser = userRepository.findById(userId);
		return optionalUser.orElse(null);
	}
	
	
	
	public boolean userConfirm(int userId, String loginId, String password) {
	    Optional<User> optionalUser = userRepository.findById(userId);
	    if(optionalUser.isPresent()) {
	        User user = optionalUser.get();
	        String encryptPassword = MD5HashingEncoder.encode(password);
	        return user.getLoginId().equals(loginId) && user.getPassword().equals(encryptPassword);
	    }
	    return false;
	}
	
	
	public boolean updateUser(int userId, String loginId, String password, String phoneNumber, String nickname, String email) {
		Optional<User> optionalUser = userRepository.findById(userId);
		String encyptPassword = MD5HashingEncoder.encode(password);
		 if(optionalUser.isEmpty()) {		 
			 return false;
		 }
		User user = optionalUser.get();
		user = user.toBuilder()
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean withdrawUser(int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		
		if(optionalUser.isPresent()) {
			
			User user = optionalUser.get();
			
			try {
				userRepository.delete(user);
			}catch(PersistenceException e) {
				return false;
			}
			
		}else {
			return false;
		}
		
		return true;
	}
	
	
	public User getUserByKakaoId(long kakaoId){
		User user = userRepository.findByKakaoIdAndLoginType(kakaoId, "KAKAO");
		return user;
	}
	
	
	public boolean addUserByKakao(long kakaoId, String phoneNumber, String nickname, String email) {
		User user = User.builder()
				.kakaoId(kakaoId)
				.phoneNumber(phoneNumber)
				.nickname(nickname)
				.email(email)
				.loginType("KAKAO")
				.build();
		try {
			userRepository.save(user);
		}catch(PersistenceException e) {
			return false;
		}
		return true;
	}
	
	
	
	public boolean updateKakaoUser(long kakaoId, String phoneNumber, String nickname, String email) {
		User user = userRepository.findByKakaoId(kakaoId);
		user = user.toBuilder()
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
	
	
	public boolean withdrawKakaoUser(long kakaoId) {
		User user = userRepository.findByKakaoId(kakaoId);
		try {
			userRepository.delete(user);
		}catch(PersistenceException e) {
			return false;
		}
		return true;
	}
	
	
	
	
	
	
}
