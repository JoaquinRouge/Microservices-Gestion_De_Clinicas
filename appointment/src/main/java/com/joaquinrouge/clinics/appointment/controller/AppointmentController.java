package com.joaquinrouge.clinics.appointment.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
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

import com.auth0.jwt.interfaces.DecodedJWT;
import com.joaquinrouge.clinics.appointment.dto.UpdateAppointmentDto;
import com.joaquinrouge.clinics.appointment.model.Appointment;
import com.joaquinrouge.clinics.appointment.service.IAppointmentService;
import com.joaquinrouge.clinics.appointment.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

	private final IAppointmentService appointmentService;
	private final JwtUtils jwtUtils;
	
	public AppointmentController(IAppointmentService appointmentService,JwtUtils jwtUtils) {
		this.appointmentService = appointmentService;
		this.jwtUtils = jwtUtils;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.findAll();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<?> getAppointmentsByDate(@PathVariable @DateTimeFormat(
    		iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        try {
            List<Appointment> appointments = appointmentService.findByDate(date);
            return ResponseEntity.ok(appointments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/patient/{id}")
    @PreAuthorize("isAuthenticated() or hasRole('RECEPTIONIST')")
    public ResponseEntity<?> findByPatientId(@PathVariable Long id,HttpServletRequest request){
    	try {
    		
    		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    		
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                		"Missing or invalid Authorization header");
            }
            
            String token = authHeader.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validateJWT(token);
    		
            String authorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();
            
            boolean isReceptionist = authorities.contains("ROLE_RECEPTIONIST");
            
            if(!isReceptionist && jwtUtils.getSpecificClaim(decodedJWT, "id").asLong() != id) {
            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                		"Unauthorized");
            }
            
    		List<Appointment> appointments = appointmentService.findByPatientId(id);
    		return ResponseEntity.ok(appointments);
    		
    	}catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment,
    		HttpServletRequest request) {
        try {
        	
        	String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    		
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                		"Missing or invalid Authorization header");
            }
            
            String token = authHeader.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validateJWT(token);
        	
            if(jwtUtils.getSpecificClaim(decodedJWT, "id").asLong() != appointment.getPatientId()) {
            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                		"Unauthorized");
            }
            
            Appointment created = appointmentService.createAppointment(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<?> updateAppointment(@RequestBody UpdateAppointmentDto appointmentDto) {
        try {
            Appointment updated = appointmentService.updateAppointment(appointmentDto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated() or hasRole('RECEPTIONIST')")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id,
    		HttpServletRequest request) {
        try {
        	
        	String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    		
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                		"Missing or invalid Authorization header");
            }
            
            String token = authHeader.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validateJWT(token);
        	
            if(jwtUtils.getSpecificClaim(decodedJWT, "id").asLong() != appointmentService.
            		findById(id).getPatientId()) {
            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                		"Unauthorized");
            }
            
            appointmentService.deleteAppointment(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
	
}
