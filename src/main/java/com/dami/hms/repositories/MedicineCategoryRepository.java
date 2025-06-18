package com.dami.hms.repositories;

import com.dami.hms.entities.MedicineCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineCategoryRepository extends JpaRepository<MedicineCategory, String> {

}
