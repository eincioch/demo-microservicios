package com.example.demo.model.service;

import com.example.demo.model.entity.Provincia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProvinciaService {

	public List<Provincia> findAll();
	
	public Page<Provincia> findAll(Pageable pageable);
	
	public Provincia findById(Long id);
	
	public Provincia save(Provincia provincia);
	
	public void delete(Long id);

}
