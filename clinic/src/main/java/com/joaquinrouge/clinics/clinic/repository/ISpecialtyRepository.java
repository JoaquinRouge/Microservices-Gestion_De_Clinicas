package com.joaquinrouge.clinics.clinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquinrouge.clinics.clinic.model.Specialty;

@Repository
public interface ISpecialtyRepository extends JpaRepository<Specialty, Long>{
	Optional<Specialty> findBySpecialty(String specialty);
	boolean existsBySpecialty(String specialty);
}
