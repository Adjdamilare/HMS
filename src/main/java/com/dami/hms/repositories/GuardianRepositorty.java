package com.dami.hms.repositories;

import com.dami.hms.entities.GuardianDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianRepositorty extends JpaRepository<GuardianDetail, String> {
}
