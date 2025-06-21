package com.dami.hms.repositories;

import com.dami.hms.entities.VisitDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<VisitDetail, String> {
}
