package com.joaquinrouge.clinics.appointment.dto;

import java.time.LocalDateTime;

public record UpdateAppointmentDto(Long id,LocalDateTime date,Long doctorId) {

}
