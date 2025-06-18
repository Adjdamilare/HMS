package com.dami.hms.repositories;

import com.dami.hms.entities.RoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomDetailsRepository extends JpaRepository<RoomDetail, String>{

    @Query("SELECT r FROM RoomDetail r WHERE r.status = 0 AND r.roomId = :roomId")
    RoomDetail findByRoomId(String roomId);

    @Query("SELECT r FROM RoomDetail r WHERE r.status = 0")
    List<RoomDetail> findAllRoomDetails();

    @Query("SELECT r FROM RoomDetail r WHERE UPPER(r.roomId) LIKE UPPER(CONCAT('%', :roomId, '%')) AND r.status = 0")
    List<RoomDetail> findByRoomIdContainingIgnoreCase(String roomId);

    @Query("SELECT r FROM RoomDetail r WHERE UPPER(r.roomType) LIKE UPPER(CONCAT('%', :roomType, '%')) AND r.status = 0")
    List<RoomDetail> findByRoomTypeContainingIgnoreCase(String roomType);

    @Query("SELECT r FROM RoomDetail r WHERE UPPER(r.roomDescription) LIKE UPPER(CONCAT('%', :roomDescription, '%')) AND r.status = 0")
    List<RoomDetail> findByRoomDescriptionContainingIgnoreCase(String roomDescription);

    @Modifying
    @Query("UPDATE RoomDetail r SET r.status = 1 WHERE r.roomId = :roomId")
    void deleteByRoomId(String roomId);
}
