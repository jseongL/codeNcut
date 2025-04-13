package com.jsL.codeNcut.daily.like.domain;

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

@Builder
@NoArgsConstructor
@AllArgsConstructor	
@Getter
@Table(name="`dailylike`")
@Entity
public class DailyLike {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int dailyId;
	private int userId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
