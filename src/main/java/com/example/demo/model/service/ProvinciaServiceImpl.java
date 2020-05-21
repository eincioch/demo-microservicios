package com.example.demo.model.service;

import com.example.demo.model.dao.IProvinciaDao;
import com.example.demo.model.entity.Provincia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProvinciaServiceImpl implements IProvinciaService {

	@Autowired
	private IProvinciaDao provinciaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Provincia> findAll() {
		return (List<Provincia>) provinciaDao.findAll();
	}


	@Override
	@Transactional(readOnly = true)
	public Page<Provincia> findAll(Pageable pageable) {
		return provinciaDao.findAll(pageable);
	}


	@Override
	@Transactional(readOnly = true)
	public Provincia findById(Long id) {
		return provinciaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional()
	public Provincia save(Provincia provincia) {
		return provinciaDao.save(provincia);
	}

	@Override
	@Transactional()
	public void delete(Long id) {
		provinciaDao.deleteById(id);
	}

}
