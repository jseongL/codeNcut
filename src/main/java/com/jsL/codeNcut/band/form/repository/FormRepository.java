package com.jsL.codeNcut.band.form.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsL.codeNcut.band.form.domain.Form;


public interface FormRepository extends JpaRepository<Form, Integer>{
	public List<Form>findByBandId(int id);
}
