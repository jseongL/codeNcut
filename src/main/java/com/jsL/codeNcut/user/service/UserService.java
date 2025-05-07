package com.jsL.codeNcut.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jsL.codeNcut.akbo.repository.AkboRepository;
import com.jsL.codeNcut.band.form.repository.FormRepository;
import com.jsL.codeNcut.band.repository.BandRepository;
import com.jsL.codeNcut.buy.domain.BuyDocument;
import com.jsL.codeNcut.buy.repository.BuyRepository;
import com.jsL.codeNcut.buy.repository.BuySearchRepository;
import com.jsL.codeNcut.daily.comment.repository.CommentRepository;
import com.jsL.codeNcut.daily.like.repository.DailyLikeRepository;
import com.jsL.codeNcut.daily.repository.DailyRepository;
import com.jsL.codeNcut.user.common.MD5HashingEncoder;
import com.jsL.codeNcut.user.domain.User;
import com.jsL.codeNcut.user.repository.UserRepository;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final AkboRepository akboRepository;
	private final FormRepository formRepository;
	private final BandRepository bandRepository;
	private final BuyRepository buyRepository;
	private final CommentRepository commentRepository;
	private final DailyLikeRepository dailyLikeRepository;
	private final DailyRepository dailyRepository;
	private final BuySearchRepository buySearchRepository;

	public UserService(
	    UserRepository userRepository,
	    AkboRepository akboRepository,
	    FormRepository formRepository,
	    BandRepository bandRepository,
	    BuyRepository buyRepository,
	    CommentRepository commentRepository,
	    DailyLikeRepository dailyLikeRepository,
	    DailyRepository dailyRepository,
	    BuySearchRepository buySearchRepository
	) {
	    this.userRepository = userRepository;
	    this.akboRepository = akboRepository;
	    this.formRepository = formRepository;
	    this.bandRepository = bandRepository;
	    this.buyRepository = buyRepository;
	    this.commentRepository = commentRepository;
	    this.dailyLikeRepository = dailyLikeRepository;
	    this.dailyRepository = dailyRepository;
	    this.buySearchRepository = buySearchRepository;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Transactional
	public boolean withdrawUser(int userId) {
	    Optional<User> optionalUser = userRepository.findById(userId);

	    if (optionalUser.isEmpty()) {
	        return false;
	    }

	    try {
	        // 댓글 삭제
	        commentRepository.deleteByUserId(userId);
	        
	        // 좋아요 삭제
	        dailyLikeRepository.deleteByUserId(userId);
	        
	        // 구매기록 삭제
	        buyRepository.deleteByUserId(userId);
	        
	        // 밴드 게시글 삭제
	        bandRepository.deleteByUserId(userId);
	        
	        // 일기 게시글 삭제
	        dailyRepository.deleteByUserId(userId);
	        
	        // 악보 삭제
	        akboRepository.deleteByUserId(userId);
	        
	        // 신청서 삭제
	        formRepository.deleteByUserId(userId);
	        
	        // 마지막에 유저 삭제
	        userRepository.delete(optionalUser.get());
	        
	        
	        //es 데이터 삭제
	        List<BuyDocument> docs = buySearchRepository.findByUserId(userId);
	        buySearchRepository.deleteAll(docs);

	        
	        
	    } catch (PersistenceException e) {
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
	
	
	@Transactional
	public boolean withdrawKakaoUser(long kakaoId) {
	    User user = userRepository.findByKakaoId(kakaoId);

	    if (user == null) return false;

	    int userId = user.getId(); 

	    try {
	        commentRepository.deleteByUserId(userId);
	        dailyRepository.deleteByUserId(userId);
	        dailyLikeRepository.deleteByUserId(userId);
	        formRepository.deleteByUserId(userId);
	        akboRepository.deleteByUserId(userId);
	        buyRepository.deleteByUserId(userId);
	        bandRepository.deleteByUserId(userId); 

	        userRepository.delete(user);
	    } catch (Exception e) {
	        return false;
	    }

	    return true;
	}
	
	
	
	
	
	
}
