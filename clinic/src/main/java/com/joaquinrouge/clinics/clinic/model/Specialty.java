package com.joaquinrouge.clinics.clinic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "specialties")
public class Specialty {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String specialty;
	
	public Specialty() {
		
	}

	public Specialty(Long id, String specialty) {
		super();
		this.id = id;
		this.specialty = specialty;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String speciality) {
		this.specialty = speciality;
	}
	
}
