package com.dami.hms.repositories;

import com.dami.hms.entities.DoctorScheduleDetail;
import com.dami.hms.entities.ServiceScheduleDetail;
import com.dami.hms.entities.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceScheduleDetailRepository extends JpaRepository<ServiceScheduleDetail, String> {
    @Query("SELECT d FROM ServiceScheduleDetail d WHERE d.status = 0")
    List<ServiceScheduleDetail> findAllActive();

    @Query("SELECT d FROM ServiceScheduleDetail d WHERE UPPER(d.serviceScheduleId) LIKE UPPER(CONCAT('%', :serviceScheduleId, '%')) AND d.status = 0")
    ServiceScheduleDetail findByServiceScheduleId(String serviceScheduleId);

    @Modifying
    @Query("UPDATE ServiceScheduleDetail d SET d.status = 1 WHERE d.serviceScheduleId = :serviceScheduleId")
    void delete(@Param("serviceScheduleId") String serviceScheduleId);

    @Query("SELECT d FROM ServiceScheduleDetail d WHERE d.status = 0")
    List<ServiceScheduleDetail> findAll();

    @Query("SELECT d FROM ServiceScheduleDetail d WHERE UPPER(d.serviceScheduleId) LIKE UPPER(CONCAT('%', :serviceScheduleId, '%')) AND d.status = 0")
    List<ServiceScheduleDetail> findByServiceScheduleIdContainingIgnoreCase(String serviceScheduleId);

    @Query("SELECT d FROM ServiceScheduleDetail d WHERE UPPER(d.serviceId) LIKE UPPER(CONCAT('%', :serviceId, '%')) AND d.status = 0")
    List<ServiceScheduleDetail> findByServiceIdContainingIgnoreCase(String serviceId);

//    @Query("SELECT d FROM ServiceScheduleDetail d WHERE UPPER(d.serviceStarts) LIKE UPPER(CONCAT('%', :serviceStarts, '%')) AND d.status = 0")
//    List<ServiceScheduleDetail> findByServiceStartsContainingIgnoreCase(String serviceStarts);
//
//    @Query("SELECT d FROM ServiceScheduleDetail d WHERE UPPER(d.serviceEnds) LIKE UPPER(CONCAT('%', :serviceEnds, '%')) AND d.status = 0")
//    List<ServiceScheduleDetail> findByServiceEndsContainingIgnoreCase(String serviceEnds);

//This method is used to find a list of DoctorScheduleDetail objects where the doctorAvailDate field contains the given availDate parameter, ignoring case, and the status field is equal to 0
    @Query("SELECT d FROM ServiceScheduleDetail d WHERE UPPER(d.serviceAvailDate) LIKE UPPER(CONCAT('%', :serviceAvailDate, '%')) AND d.status = 0")
    List<ServiceScheduleDetail> findByServiceAvailDateContainingIgnoreCase(String serviceAvailDate);

    @Query("SELECT d FROM ServiceScheduleDetail d WHERE UPPER(d.scheduleNotes) LIKE UPPER(CONCAT('%', :notes, '%')) AND d.status = 0")
    List<ServiceScheduleDetail> findByScheduleNotesContainingIgnoreCase(String notes);

}
