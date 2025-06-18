package com.dami.hms.repositories;

import com.dami.hms.entities.Doctor;
import com.dami.hms.entities.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services, String> {
    @Query("SELECT s FROM Services s WHERE s.channelServiceId = :id AND s.status = 0")
    Services findByChannelServiceId(String id);

    @Query("SELECT s FROM Services s WHERE UPPER(s.channelServiceId) LIKE UPPER(CONCAT('%', :id, '%')) AND s.status = 0")
    List<Services> findByChannelServiceIdContainingIgnoreCase(String id);

    @Query("SELECT s FROM Services s WHERE UPPER(s.channelService) LIKE UPPER(CONCAT('%', :channelService, '%')) AND s.status = 0")
    List<Services> findByChannelServiceContainingIgnoreCase(String channelService);

    @Query("SELECT s FROM Services s WHERE s.durationOfService >= :duration AND s.status = 0")
    List<Services> findByDurationOfServiceGreaterThanEqual(Double duration);

    @Query("SELECT s FROM Services s WHERE s.chargeForService >= :charge AND s.status = 0")
    List<Services> findByChargeForServiceGreaterThanEqual(Double charge);

    @Query("SELECT s FROM Services s WHERE UPPER(s.serviceNotes) LIKE UPPER(CONCAT('%', :notes, '%')) AND s.status = 0")
    List<Services> findByServiceNotesContainingIgnoreCase(String notes);

    @Query("SELECT s FROM Services s WHERE s.status = 0")
    List<Services> findByChannelServiceIdContainingIgnoreCaseOrChannelServiceContainingIgnoreCaseOrServiceNotesContainingIgnoreCase(String channelServiceId, String channelService, String serviceNotes);

    @Query("Select e from Services e where e.status = 0")
    List<Services> findAll();

    @Modifying
    @Query("update Services e set e.status = 1 where e.channelServiceId = :channelServiceId")
    void softDelete(@Param("channelServiceId") String channelServiceId);
}
