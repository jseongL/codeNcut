package com.jsL.codeNcut.band.form.domain;

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
@Table(name="`form`")
@Entity
public class Form {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int bandId;
	private String phoneNumber;
	private String place;
	private String experience;
	private String introduce;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	

}
