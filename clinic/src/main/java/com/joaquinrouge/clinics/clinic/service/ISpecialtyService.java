package com.joaquinrouge.clinics.clinic.service;

import java.util.List;


import com.joaquinrouge.clinics.clinic.model.Specialty;

public interface ISpecialtyService {
	
	List<Specialty> findAll();
	Specialty findById(Long id);
	Specialty createSpecialty(Specialty specialty);
	void deleteSpecialty(Long id);
	Specialty updateSpecialty(Specialty specialty);
}
