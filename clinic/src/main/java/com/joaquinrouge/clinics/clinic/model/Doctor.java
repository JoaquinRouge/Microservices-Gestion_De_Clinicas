package com.joaquinrouge.clinics.clinic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	
	private String fullName;
	
	private String licenseNumber;
	
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id", nullable = false)
	private Specialty specialty;
	
	public Doctor() {
		
	}

	public Doctor(Long id, Long userId, String fullName, String licenseNumber, Specialty specialty) {
		super();
		this.id = id;
		this.userId = userId;
		this.fullName = fullName;
		this.licenseNumber = licenseNumber;
		this.specialty = specialty;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty speciality) {
		this.specialty = speciality;
	}
	
}
