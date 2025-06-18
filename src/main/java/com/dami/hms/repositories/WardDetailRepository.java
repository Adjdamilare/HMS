package com.dami.hms.repositories;

import com.dami.hms.entities.WardDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface WardDetailRepository extends JpaRepository<WardDetail, String> {
    @Query("SELECT r FROM WardDetail r WHERE r.status = 0 AND r.wardId = :wardId")
    WardDetail findByWardId(String wardId);

    @Query("SELECT r FROM WardDetail r WHERE r.status = 0")
    List<WardDetail> findAllWardDetails();

    @Query("SELECT r FROM WardDetail r WHERE UPPER(r.wardId) LIKE UPPER(CONCAT('%', :wardId, '%')) AND r.status = 0")
    List<WardDetail> findByWardIdContainingIgnoreCase(String wardId);

    @Query("SELECT r FROM WardDetail r WHERE UPPER(r.wardName) LIKE UPPER(CONCAT('%', :wardName, '%')) AND r.status = 0")
    List<WardDetail> findByWardNameContainingIgnoreCase(String wardName);

    @Query("SELECT r FROM WardDetail r WHERE UPPER(r.wardDesc) LIKE UPPER(CONCAT('%', :wardDesc, '%')) AND r.status = 0")
    List<WardDetail> findByWardDescContainingIgnoreCase(String wardDesc);

    @Query("SELECT r FROM WardDetail r WHERE r.wardRate >= :wardRate AND r.status = 0")
    List<WardDetail> findByWardRateGreaterThanEqual(BigDecimal wardRate);

    @Modifying
    @Query("UPDATE WardDetail r SET r.status = 1 WHERE r.wardId = :wardId")
    void deleteByWardId(String wardId);
}
