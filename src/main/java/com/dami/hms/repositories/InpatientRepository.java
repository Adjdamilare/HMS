package com.dami.hms.repositories;

import com.dami.hms.entities.Inpatient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InpatientRepository extends JpaRepository<Inpatient, String> {
    // Find only active inpatients (status = 0)
    List<Inpatient> findByStatusOrderByPatientIdAsc(Byte status);

    // Find by patient ID with status check
    Optional<Inpatient> findByPatientIdAndStatus(String patientId, Byte status);
}
