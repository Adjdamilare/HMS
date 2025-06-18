package com.dami.hms.repositories;

import com.dami.hms.entities.RoomDetail;
import com.dami.hms.entities.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, String> {

    @Query("SELECT r FROM RoomType r WHERE r.status = 0 AND r.roomType = :roomType")
    RoomType findByRoomType(String roomType);

    @Query("SELECT r FROM RoomType r WHERE r.status = 0")
    List<RoomType> findAllRoomTypes();

    @Query("SELECT r FROM RoomType r WHERE UPPER(r.roomType) LIKE UPPER(CONCAT('%', :roomType, '%')) AND r.status = 0")
    List<RoomType> findByRoomTypeContainingIgnoreCase(String roomType);

    @Query("SELECT r FROM RoomType r WHERE r.roomRates >= :roomRates AND r.status = 0")
    List<RoomType> findByRoomRates(BigDecimal roomRates);

    @Query("SELECT r FROM RoomType r WHERE UPPER(r.notes) LIKE UPPER(CONCAT('%', :notes, '%')) AND r.status = 0")
    List<RoomType> findByNotesContainingIgnoreCase(String notes);

    @Modifying
    @Query("UPDATE RoomType r SET r.status = 1 WHERE r.roomType = :roomType")
    void deleteByRoomType(String roomType);
}
