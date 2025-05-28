package com.joaquinrouge.clinics.clinic.service;

import java.util.List;

import com.joaquinrouge.clinics.clinic.model.Doctor;
import com.joaquinrouge.clinics.clinic.model.Specialty;

public interface IDoctorService {

	Doctor findById(Long id);
	List<Doctor> findAll();
	List<Doctor> findBySpeciality(Specialty speciality);
	Doctor findByFullName(String fullName);
	Doctor findByLicenseNumber(String licenseNumber);
	Doctor createDoctor(Doctor doctor);
	void deleteDoctor(Long id);
	Doctor updateDoctor(Doctor doctor);
}
