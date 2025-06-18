package com.dami.hms.repositories;

import com.dami.hms.entities.Outpatient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OutpatientRepository extends JpaRepository<Outpatient, String> {
    // Find only active outpatients (active = 0)
    List<Outpatient> findByActiveOrderByPatientIdAsc(Byte active);

    // Find by patient ID with active status check
    Optional<Outpatient> findByPatientIdAndActive(String patientId, Byte active);
}
