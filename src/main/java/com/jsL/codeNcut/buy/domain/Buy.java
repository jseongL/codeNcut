package com.jsL.codeNcut.buy.domain;

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
@Table(name="`buy`")
@Entity
public class Buy {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int userId;
	private String description;
	private String model;
	private int buyYear;
	private int price;
	private String imgPath;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
