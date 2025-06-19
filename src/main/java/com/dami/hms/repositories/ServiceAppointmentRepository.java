package com.dami.hms.repositories;

import com.dami.hms.entities.ServiceAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceAppointmentRepository extends JpaRepository<ServiceAppointment, String> {
}
