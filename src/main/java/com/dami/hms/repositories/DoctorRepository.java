package com.dami.hms.repositories;

import com.dami.hms.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
    Doctor findByDoctorId(String id);

    List<Doctor> findByDoctorIdContainingIgnoreCase(String id);

    List<Doctor> findByDoctorFnameContainingIgnoreCase(String query);

    List<Doctor> findByDoctorLnameContainingIgnoreCase(String query);

    List<Doctor> findByDoctorSexContainingIgnoreCase(String query);

    List<Doctor> findByDoctorNidContainingIgnoreCase(String query);

    List<Doctor> findByDoctorHphoneContainingIgnoreCase(String query);

    List<Doctor> findByDoctorMphoneContainingIgnoreCase(String query);

    List<Doctor> findByDoctorAddressContainingIgnoreCase(String query);

    List<Doctor> findByDoctorQualificationContainingIgnoreCase(String query);

    List<Doctor> findByDoctorSpecializationContainingIgnoreCase(String query);

    List<Doctor> findByDoctorTypeContainingIgnoreCase(String query);

    List<Doctor> findByDoctorVchargeGreaterThanEqual(Double charge);

    List<Doctor> findByDoctorCchargeGreaterThanEqual(Double charge);

    List<Doctor> findByDoctorBasicSalGreaterThanEqual(Double salary);
}
