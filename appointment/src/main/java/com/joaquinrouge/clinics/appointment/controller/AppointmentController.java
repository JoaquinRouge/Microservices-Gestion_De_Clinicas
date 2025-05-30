package com.joaquinrouge.clinics.appointment.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaquinrouge.clinics.appointment.dto.UpdateAppointmentDto;
import com.joaquinrouge.clinics.appointment.model.Appointment;
import com.joaquinrouge.clinics.appointment.service.IAppointmentService;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

	private IAppointmentService appointmentService;
	
	public AppointmentController(IAppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
	
	@GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.findAll();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> getAppointmentsByDate(@PathVariable @DateTimeFormat(
    		iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        try {
            List<Appointment> appointments = appointmentService.findByDate(date);
            return ResponseEntity.ok(appointments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        try {
            Appointment created = appointmentService.createAppointment(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAppointment(@RequestBody UpdateAppointmentDto appointmentDto) {
        try {
            Appointment updated = appointmentService.updateAppointment(appointmentDto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
	
	
}
