package com.jsL.codeNcut.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsL.codeNcut.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	    public User findByLoginIdAndPassword(String loginId, String password);
	    public int countByLoginId(String loginId);	   
	    public User findByLoginId(String loginId);
	    public User findByKakaoIdAndLoginType(long kakaoId, String loginType);
	    public User findByKakaoId(long kakaoId);
}
