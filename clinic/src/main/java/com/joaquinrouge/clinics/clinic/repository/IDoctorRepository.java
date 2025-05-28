package com.joaquinrouge.clinics.clinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquinrouge.clinics.clinic.model.Doctor;
import com.joaquinrouge.clinics.clinic.model.Specialty;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor, Long>{

	Optional<List<Doctor>> findBySpecialty(Specialty speciality);
	Optional<Doctor> findByFullName(String fullName);
	Optional<Doctor> findByLicenseNumber(String licenseNumber);
	boolean existsByLicenseNumber(String licenseNumber);
	
}
