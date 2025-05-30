package com.joaquinrouge.clinics.appointment.service;

import java.time.LocalDateTime;
import java.util.List;

import com.joaquinrouge.clinics.appointment.dto.UpdateAppointmentDto;
import com.joaquinrouge.clinics.appointment.model.Appointment;

public interface IAppointmentService {
	
	Appointment findById(Long id);
	List<Appointment> findAll();
	List<Appointment> findByDate(LocalDateTime date);
	Appointment createAppointment(Appointment appointment);
	void deleteAppointment(Long id);
	Appointment updateAppointment(UpdateAppointmentDto appointment);
}
