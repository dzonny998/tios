package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Sud;
import com.example.demo.repository.SudRepository;

@Service
public class SudService {
	
	@Autowired
	private SudRepository sudRepository;
	
	public List<Sud> getAllSudovi(){
		return sudRepository.findAll();
	}

	public Optional<Sud> getSudById(int sudId) {
		return sudRepository.findById(sudId);
	}
	
	public boolean existsById(int id) {
		return getSudById(id).isPresent();
	}
	
	public List<Sud> getAllSudoviByNaziv(String naziv){
		return sudRepository.findByNazivContainingIgnoreCase(naziv);
	}

	public Sud updateSud(int id, Sud sud) {
		var sudOptional = getSudById(id);
		if (sudOptional.isEmpty()) {
			return null;
		}

		var sudDb = sudOptional.get();
		sudDb.setAdresa(sud.getAdresa());
		sudDb.setNaziv(sud.getNaziv());

		return sudRepository.save(sudDb);
	}
	
	public Sud addSud(Sud sud) {
		return sudRepository.save(sud);
	}
	
	public void deleteById(int id) {
		sudRepository.deleteById(id);
	}
}
