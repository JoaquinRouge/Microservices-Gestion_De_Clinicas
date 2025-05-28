package com.joaquinrouge.clinics.clinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joaquinrouge.clinics.clinic.model.Specialty;
import com.joaquinrouge.clinics.clinic.repository.ISpecialtyRepository;

@Service
public class SpecialtyService implements ISpecialtyService{

	private ISpecialtyRepository specialtyRepo;
	
	public SpecialtyService(ISpecialtyRepository specialtyRepo) {
		this.specialtyRepo = specialtyRepo;
	}
	
	@Override
	public List<Specialty> findAll() {
		return specialtyRepo.findAll();
	}

	@Override
	public Specialty findById(Long id) {
		return specialtyRepo.findById(id).orElseThrow(()->
		new IllegalArgumentException("Specialty not found"));
	}

	@Override
	public Specialty createSpecialty(Specialty specialty) {
		
		if(specialtyRepo.existsBySpecialty(specialty.getSpecialty())) {
			throw new IllegalArgumentException("Specialty already exists");
		}
		
		return specialtyRepo.save(specialty);
	}

	@Override
	public void deleteSpecialty(Long id) {
		
		if(!specialtyRepo.existsById(id)) {
			throw new IllegalArgumentException("Specialty not found");
		}
		
	}

	@Override
	public Specialty updateSpecialty(Specialty specialty) {
		
		if(specialtyRepo.existsBySpecialty(specialty.getSpecialty())) {
			throw new IllegalArgumentException("Specialty already exists");
		}
		
		Specialty specFromDb = findById(specialty.getId());
		
		specFromDb.setSpecialty(specialty.getSpecialty());
		
		return specialtyRepo.save(specFromDb);
	}

}
