package com.joaquinrouge.clinics.appointment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.joaquinrouge.clinics.appointment.dto.UpdateAppointmentDto;
import com.joaquinrouge.clinics.appointment.model.Appointment;
import com.joaquinrouge.clinics.appointment.repository.IAppointmentRepository;

@Service
public class AppointmentService implements IAppointmentService {

	private IAppointmentRepository appRepo;
	
	public AppointmentService(IAppointmentRepository appRepo) {
		this.appRepo = appRepo;
	}
	
	@Override
	public Appointment findById(Long id) {
		return appRepo.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Appointment not found"));
	}
	
	@Override
	public List<Appointment> findAll() {
		return appRepo.findAll();
	}

	@Override
	public List<Appointment> findByDate(LocalDateTime date) {
		return appRepo.findByDate(date).orElseThrow(
				()-> new IllegalArgumentException("Appointment not found"));
	}

	@Override
	public List<Appointment> findByPatientId(Long id){
		return appRepo.findByPatientId(id).orElseThrow(
				()-> new IllegalArgumentException("Appointments not found"));
	}
	
	@Override
	public Appointment createAppointment(Appointment appointment) {
		
		return appRepo.save(appointment);
	}

	@Override
	public void deleteAppointment(Long id) {
		if(!appRepo.existsById(id)) {
			throw new IllegalArgumentException("Appointment not found");
		}
		
		appRepo.deleteById(id);
		
	}

	@Override
	public Appointment updateAppointment(UpdateAppointmentDto appointment) {
		
		Appointment appointmentFromDb = findById(appointment.id());
		
		appointmentFromDb.setDate(appointment.date());
		appointmentFromDb.setDoctorId(appointment.doctorId());
		
		return appRepo.save(appointmentFromDb);
	}

}
