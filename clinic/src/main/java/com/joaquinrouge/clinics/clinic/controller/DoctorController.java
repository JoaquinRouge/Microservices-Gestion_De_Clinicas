package com.joaquinrouge.clinics.clinic.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaquinrouge.clinics.clinic.model.Doctor;
import com.joaquinrouge.clinics.clinic.model.Specialty;
import com.joaquinrouge.clinics.clinic.service.IDoctorService;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

	private IDoctorService doctorService;
	
	public DoctorController(IDoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
    @GetMapping
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('RECEPTIONIST') or hasRole('DOCTOR')")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(doctorService.findById(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/speciality")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<?> getDoctorsBySpeciality(@RequestBody Specialty speciality) {
        try {
            return ResponseEntity.ok(doctorService.findBySpeciality(speciality));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/name/{fullName}")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<?> getDoctorByFullName(@PathVariable String fullName) {
        try {
            return ResponseEntity.ok(doctorService.findByFullName(fullName));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/license/{licenseNumber}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPTIONIST')")
    public ResponseEntity<?> getDoctorByLicenseNumber(@PathVariable String licenseNumber) {
        try {
            return ResponseEntity.ok(doctorService.findByLicenseNumber(licenseNumber));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(doctor));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        try {
            doctor.setId(id);
            return ResponseEntity.ok(doctorService.updateDoctor(doctor));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
	
	
}
