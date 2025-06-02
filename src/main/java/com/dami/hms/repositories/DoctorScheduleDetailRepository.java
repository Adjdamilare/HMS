package com.dami.hms.repositories;

import com.dami.hms.entities.DoctorScheduleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorScheduleDetailRepository extends JpaRepository<DoctorScheduleDetail, String> {
    @Query("SELECT d FROM DoctorScheduleDetail d WHERE d.status = 0")
    List<DoctorScheduleDetail> findAllActive();

    @Modifying
    @Query("UPDATE DoctorScheduleDetail d SET d.status = 1 WHERE d.doctorScheduleId = :doctorScheduleId")
    void delete(@Param("doctorScheduleId") String doctorScheduleId);

    @Query("SELECT d FROM DoctorScheduleDetail d WHERE d.status = 0")
    List<DoctorScheduleDetail> findAll();


    @Query("SELECT d FROM DoctorScheduleDetail d WHERE UPPER(d.doctorScheduleId) LIKE UPPER(CONCAT('%', :doctorScheduleId, '%')) AND d.status = 0")
    List<DoctorScheduleDetail> findByDoctorScheduleIdContainingIgnoreCase(String doctorScheduleId);

    @Query("SELECT d FROM DoctorScheduleDetail d WHERE UPPER(d.doctorId) LIKE UPPER(CONCAT('%', :doctorId, '%')) AND d.status = 0")
    List<DoctorScheduleDetail> findByDoctorIdContainingIgnoreCase(String doctorScheduleId);

//    @Query("SELECT d FROM DoctorScheduleDetail d WHERE UPPER(d.doctorIn) LIKE UPPER(CONCAT('%', :doctorIn, '%')) AND d.status = 0")
//    List<DoctorScheduleDetail> findByDoctorInContainingIgnoreCase(String doctorIn);
//
//    @Query("SELECT d FROM DoctorScheduleDetail d WHERE UPPER(d.doctorOut) LIKE UPPER(CONCAT('%', :doctorOut, '%')) AND d.status = 0")
//    List<DoctorScheduleDetail> findByDoctorOutContainingIgnoreCase(String doctorOut);

    @Query("SELECT d FROM DoctorScheduleDetail d WHERE UPPER(d.doctorAvailDate) LIKE UPPER(CONCAT('%', :availDate, '%')) AND d.status = 0")
    List<DoctorScheduleDetail> findByDoctorAvailDateContainingIgnoreCase(String availDate);

    @Query("SELECT d FROM DoctorScheduleDetail d WHERE UPPER(d.scheduleNotes) LIKE UPPER(CONCAT('%', :notes, '%')) AND d.status = 0")
    List<DoctorScheduleDetail> findByScheduleNotesContainingIgnoreCase(String notes);

    @Query("SELECT d FROM DoctorScheduleDetail d WHERE UPPER(d.doctorId) = :doctorId AND d.status = 0")
    DoctorScheduleDetail findByDoctorScheduleId(String doctorId);
}
