package com.joaquinrouge.clinics.appointment.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquinrouge.clinics.appointment.model.Appointment;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long>{
	Optional<List<Appointment>> findByDate(LocalDateTime date);
	Optional<List<Appointment>> findByPatientId(Long id);
}
