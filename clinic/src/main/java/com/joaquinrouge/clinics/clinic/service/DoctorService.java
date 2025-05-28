package com.joaquinrouge.clinics.clinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.joaquinrouge.clinics.clinic.model.Doctor;
import com.joaquinrouge.clinics.clinic.model.Specialty;
import com.joaquinrouge.clinics.clinic.repository.IDoctorRepository;

@Service
public class DoctorService implements IDoctorService {

	private IDoctorRepository doctorRepo;
	
	public DoctorService(IDoctorRepository doctorRepo) {
		this.doctorRepo = doctorRepo;
	}
	
	@Override
	public List<Doctor> findAll() {
		return doctorRepo.findAll();
	}
	
	@Override
	public Doctor findById(Long id) {
		return doctorRepo.findById(id).orElseThrow(()->
		new IllegalArgumentException("Doctor not found"));
	}

	@Override
	public List<Doctor> findBySpeciality(Specialty speciality) {
		return doctorRepo.findBySpecialty(speciality).orElseThrow(()->
		new IllegalArgumentException("No doctor found for speciality " + speciality.getSpecialty()));
	}

	@Override
	public Doctor findByFullName(String fullName) {
		return doctorRepo.findByFullName(fullName).orElseThrow(()->
		new IllegalArgumentException("Doctor not found"));
	}
	
	@Override
	public Doctor findByLicenseNumber(String licenseNumber) {
		return doctorRepo.findByLicenseNumber(licenseNumber).orElseThrow(()->
		new IllegalArgumentException("Doctor not found"));
	}

	@Override
	public Doctor createDoctor(Doctor doctor) {
		
		if(doctorRepo.existsByLicenseNumber(doctor.getLicenseNumber())) {
			throw new IllegalArgumentException("Doctor already exists");
		}
		
		return doctorRepo.save(doctor);
	}

	@Override
	public void deleteDoctor(Long id) {
		if(!doctorRepo.existsById(id)) {
			throw new IllegalArgumentException("Doctor not found");
		}
		
		doctorRepo.deleteById(id);
	}

	@Override
	public Doctor updateDoctor(Doctor doctor) {
		
		if(doctorRepo.existsByLicenseNumber(doctor.getLicenseNumber())) {
			throw new IllegalArgumentException("Doctor already exists");
		}
		
		Doctor doctorFromDb = findById(doctor.getId());
		
		doctorFromDb.setFullName(doctor.getFullName());
		doctorFromDb.setLicenseNumber(doctor.getLicenseNumber());
		doctorFromDb.setSpecialty(doctor.getSpecialty());
		
		return doctorRepo.save(doctorFromDb);
	}

}
