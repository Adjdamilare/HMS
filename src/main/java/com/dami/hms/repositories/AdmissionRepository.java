package com.dami.hms.repositories;

import com.dami.hms.entities.AdmissionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRepository extends JpaRepository<AdmissionDetail, String> {
}
