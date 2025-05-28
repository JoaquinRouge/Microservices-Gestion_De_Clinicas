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

import com.joaquinrouge.clinics.clinic.model.Specialty;
import com.joaquinrouge.clinics.clinic.service.ISpecialtyService;

@RestController
@RequestMapping("/api/specialty")
public class SpecialtyController {

	private final ISpecialtyService specialtyService;

	public SpecialtyController(ISpecialtyService specialtyService) {
		this.specialtyService = specialtyService;
	}
	
	
    @GetMapping
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<List<Specialty>> getAllSpecialties() {
        return ResponseEntity.ok(specialtyService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<?> getSpecialtyById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(specialtyService.findById(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<?> createSpecialty(@RequestBody Specialty specialty) {
        try {
            Specialty created = specialtyService.createSpecialty(specialty);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<?> updateSpecialty(@PathVariable Long id, @RequestBody Specialty specialty) {
        try {
            specialty.setId(id);
            Specialty updated = specialtyService.updateSpecialty(specialty);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<?> deleteSpecialty(@PathVariable Long id) {
        try {
            specialtyService.deleteSpecialty(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }	
}
