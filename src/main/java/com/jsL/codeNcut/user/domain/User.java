package com.jsL.codeNcut.user.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="`user`")
@Entity
public class User {
	

	@Id
 	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String loginId;
	private String password;
	private String salt;
	private String phoneNumber;
	private String nickname;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
	private Long kakaoId;
	private String loginType;

}
