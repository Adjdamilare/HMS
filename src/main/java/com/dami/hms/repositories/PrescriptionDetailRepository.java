package com.dami.hms.repositories;

import com.dami.hms.entities.PrescriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetail, String> {
}
