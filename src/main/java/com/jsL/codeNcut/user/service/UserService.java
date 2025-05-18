package com.jsL.codeNcut.user.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jsL.codeNcut.akbo.repository.AkboRepository;
import com.jsL.codeNcut.band.form.repository.FormRepository;
import com.jsL.codeNcut.band.repository.BandRepository;
import com.jsL.codeNcut.buy.repository.BuyRepository;
import com.jsL.codeNcut.daily.comment.repository.CommentRepository;
import com.jsL.codeNcut.daily.like.repository.DailyLikeRepository;
import com.jsL.codeNcut.daily.repository.DailyRepository;
import com.jsL.codeNcut.user.common.PasswordUtil;
import com.jsL.codeNcut.user.common.VerifyKakaoAccessToken;
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
	private final VerifyKakaoAccessToken verify;


	public UserService(
	    UserRepository userRepository,
	    AkboRepository akboRepository,
	    FormRepository formRepository,
	    BandRepository bandRepository,
	    BuyRepository buyRepository,
	    CommentRepository commentRepository,
	    DailyLikeRepository dailyLikeRepository,
	    DailyRepository dailyRepository,
	    VerifyKakaoAccessToken verify
	) {
	    this.userRepository = userRepository;
	    this.akboRepository = akboRepository;
	    this.formRepository = formRepository;
	    this.bandRepository = bandRepository;
	    this.buyRepository = buyRepository;
	    this.commentRepository = commentRepository;
	    this.dailyLikeRepository = dailyLikeRepository;
	    this.dailyRepository = dailyRepository;
	    this.verify = verify;
	}
	
	
	
	public boolean addUser(String loginId, String password, String phoneNumber, String nickname, String email) {	
		//String encyptPassword = MD5HashingEncoder.encode(password);
		
		String salt = PasswordUtil.generateSalt();
		String hashedPassword;
		try {
			hashedPassword = PasswordUtil.hashPassword(password, salt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	
		
		User user = User.builder()
				.loginId(loginId)
				.password(hashedPassword)
				.salt(salt)
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
		
	
	
	public User getUser(String loginId, String password) throws Exception {
	    User user = userRepository.findByLoginId(loginId);
	    if (user == null) return null;

	    String hashedInput = PasswordUtil.hashPassword(password, user.getSalt());

	    if (hashedInput.equals(user.getPassword())) {//사용자가 입력한 비밀번호와 DB에 저장된 salt화 비번이랑 일치하면 유저객체 반환
	        return user;
	    }
	    return null;
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
	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get();
	        String salt = user.getSalt();
	        try {
	            String hashedPassword = PasswordUtil.hashPassword(password, salt);
	            return user.getLoginId().equals(loginId) && user.getPassword().equals(hashedPassword);
	        } catch (Exception e) {
	            return false;
	        }
	    }
	    return false;
	}
	
	
	
	public boolean updateUser(int userId, String loginId, String password, String phoneNumber, String nickname, String email) {
	    Optional<User> optionalUser = userRepository.findById(userId);
	    if (optionalUser.isEmpty()) {
	        return false;
	    }

	    User user = optionalUser.get();
	    String salt = user.getSalt(); // 기존 salt 유지
	    String hashedPassword;
	    try {
	        hashedPassword = PasswordUtil.hashPassword(password, salt);
	    } catch (Exception e) {
	        return false;
	    }

	    user = user.toBuilder()
	        .loginId(loginId)
	        .password(hashedPassword)
	        .phoneNumber(phoneNumber)
	        .nickname(nickname)
	        .email(email)
	        .build();

	    try {
	        userRepository.save(user);
	    } catch (PersistenceException e) {
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
//	        List<BuyDocument> docs = buySearchRepository.findByUserId(userId);
//	        buySearchRepository.deleteAll(docs);

	        
	        
	    } catch (PersistenceException e) {
	        return false;
	    }

	    return true;
	}
	
	
	
	
	public User getUserByKakaoId(long kakaoId, String accessToken) {
	    // 1. 카카오 API 호출해서 accessToken 검증
	    Long verifiedId = verify.verifyKakaoAccessToken(accessToken);

	    // 2. 프론트에서 보낸 kakaoId와 카카오 응답이 일치하는지 체크
	    if (!Objects.equals(verifiedId, kakaoId)) {
	        throw new RuntimeException("카카오 ID 불일치 - 위조된 토큰일 수 있음");
	    }

	    // 3. DB에서 유저 조회
	    User user =  userRepository.findByKakaoIdAndLoginType(kakaoId, "KAKAO");
	    return user;
	}
	
	
	public boolean addUserByKakao(long kakaoId, String accessToken, String phoneNumber, String nickname, String email) {
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
