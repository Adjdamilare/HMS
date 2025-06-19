package com.dami.hms.repositories;

import com.dami.hms.entities.DoctorAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DoctorAppointmentRepository extends JpaRepository<DoctorAppointment, String> {
}
