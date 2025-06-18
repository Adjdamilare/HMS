package com.dami.hms.repositories;

import com.dami.hms.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
    @Query("SELECT d FROM Doctor d WHERE d.doctorId = :id AND d.status = 0")
    Doctor findByDoctorId(@Param("id") String id);

    @Query("SELECT d FROM Doctor d WHERE UPPER(d.doctorId) LIKE UPPER(CONCAT('%', :id, '%')) AND d.status = 0")
    List<Doctor> findByDoctorIdContainingIgnoreCase(@Param("id") String id);

    @Query("SELECT d FROM Doctor d WHERE UPPER(d.doctorFname) LIKE UPPER(CONCAT('%', :query, '%')) AND d.status = 0")
    List<Doctor> findByDoctorFnameContainingIgnoreCase(@Param("query") String query);

    @Query("SELECT d FROM Doctor d WHERE UPPER(d.doctorLname) LIKE UPPER(CONCAT('%', :query, '%')) AND d.status = 0")
    List<Doctor> findByDoctorLnameContainingIgnoreCase(@Param("query") String query);

    @Query("SELECT d FROM Doctor d WHERE UPPER(d.doctorSex) LIKE UPPER(CONCAT('%', :query, '%')) AND d.status = 0")
    List<Doctor> findByDoctorSexContainingIgnoreCase(@Param("query") String query);

    @Query("SELECT d FROM Doctor d WHERE UPPER(d.doctorNid) LIKE UPPER(CONCAT('%', :query, '%')) AND d.status = 0")
    List<Doctor> findByDoctorNidContainingIgnoreCase(@Param("query") String query);

    @Query("SELECT d FROM Doctor d WHERE UPPER(d.doctorHphone) LIKE UPPER(CONCAT('%', :query, '%')) AND d.status = 0")
    List<Doctor> findByDoctorHphoneContainingIgnoreCase(@Param("query") String query);

    @Query("SELECT d FROM Doctor d WHERE UPPER(d.doctorMphone) LIKE UPPER(CONCAT('%', :query, '%')) AND d.status = 0")
    List<Doctor> findByDoctorMphoneContainingIgnoreCase(@Param("query") String query);

    @Query("SELECT d FROM Doctor d WHERE UPPER(d.doctorAddress) LIKE UPPER(CONCAT('%', :query, '%')) AND d.status = 0")
    List<Doctor> findByDoctorAddressContainingIgnoreCase(@Param("query") String query);

    @Query("SELECT d FROM Doctor d WHERE UPPER(d.doctorQualification) LIKE UPPER(CONCAT('%', :query, '%')) AND d.status = 0")
    List<Doctor> findByDoctorQualificationContainingIgnoreCase(@Param("query") String query);

    @Query("SELECT d FROM Doctor d WHERE UPPER(d.doctorSpecialization) LIKE UPPER(CONCAT('%', :query, '%')) AND d.status = 0")
    List<Doctor> findByDoctorSpecializationContainingIgnoreCase(@Param("query") String query);

    @Query("SELECT d FROM Doctor d WHERE UPPER(d.doctorType) LIKE UPPER(CONCAT('%', :query, '%')) AND d.status = 0")
    List<Doctor> findByDoctorTypeContainingIgnoreCase(@Param("query") String query);

    @Query("SELECT d FROM Doctor d WHERE d.doctorVcharge >= :charge AND d.status = 0")
    List<Doctor> findByDoctorVchargeGreaterThanEqual(@Param("charge") Double charge);

    @Query("SELECT d FROM Doctor d WHERE d.doctorCcharge >= :charge AND d.status = 0")
    List<Doctor> findByDoctorCchargeGreaterThanEqual(@Param("charge") Double charge);

    @Query("SELECT d FROM Doctor d WHERE d.doctorBasicSal >= :salary AND d.status = 0")
    List<Doctor> findByDoctorBasicSalGreaterThanEqual(@Param("salary") Double salary);

    @Query("SELECT d FROM Doctor d WHERE d.status = 0")
    List<Doctor> findAll();

    @Query("SELECT d.doctorId FROM Doctor d WHERE d.status = 0")
    List<String> findAllDoctorIds();

    @Modifying
    @Query("UPDATE Doctor d SET d.status = 1 WHERE d.doctorId = :doctorId")
    void softDelete(@Param("doctorId") String doctorId);
}