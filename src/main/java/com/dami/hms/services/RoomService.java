package com.dami.hms.services;

import com.dami.hms.entities.Doctor;
import com.dami.hms.entities.RoomDetail;
import com.dami.hms.entities.RoomType;
import com.dami.hms.repositories.RoomDetailsRepository;
import com.dami.hms.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public String generateNextRoomId() {
        Optional<RoomDetail> lastRoomOptional = roomDetailsRepository.findAll(Sort.by(Sort.Direction.DESC, "roomId")).stream().findFirst();

        if (lastRoomOptional.isPresent()) {
            String lastRoomId = lastRoomOptional.get().getRoomId();
            int lastNumber = Integer.parseInt(lastRoomId.substring(2));
            return String.format("RR%03d", lastNumber + 1);
        } else {
            return "RR001"; // First doctor
        }
    }

    //this is for roomDetails
    public List<RoomDetail> getRoomDetails() {
        return roomDetailsRepository.findAllRoomDetails();
    }
    public void saveRoomDetail(RoomDetail roomDetail) {
        roomDetailsRepository.save(roomDetail);
    }

    @Transactional
    public void deleteRoomDetail(String roomNumber) {
        roomDetailsRepository.deleteByRoomId(roomNumber);
    }
    public RoomDetail getRoomDetailById(String roomNumber) {
        return roomDetailsRepository.findByRoomId(roomNumber);
    }
    public List<RoomDetail> searchRoomDetailByColumn(String query, String searchColumn) {
        List<RoomDetail> roomDetails = new ArrayList<>();
        switch (searchColumn) {
            case "roomId":
                roomDetails.addAll(roomDetailsRepository.findByRoomIdContainingIgnoreCase(query));
                break;
            case "roomType":
                roomDetails.addAll(roomDetailsRepository.findByRoomTypeContainingIgnoreCase(query));
                break;
            case "roomDescription":
                roomDetails.addAll(roomDetailsRepository.findByRoomDescriptionContainingIgnoreCase(query));
                break;
            default:
                // Handle unknown column or do nothing
                break;
        }
        return roomDetails.stream().distinct().toList();
    }



    //this is for roomType
    public List<RoomType> getRoomTypes() {
        return roomTypeRepository.findAllRoomTypes();
    }
    public void saveRoomType(RoomType roomType) {
        roomTypeRepository.save(roomType);
    }
    @Transactional
    public void deleteRoomType(String roomType) {
        roomTypeRepository.deleteByRoomType(roomType);
    }
    public RoomType getRoomType(String roomType) {
        return roomTypeRepository.findByRoomType(roomType);
    }

    public List<RoomType> searchRoomTypeByColumn(String query, String searchColumn) {
        List<RoomType> roomTypes = new ArrayList<>();
        switch (searchColumn) {
            case "roomType":
                roomTypes.addAll(roomTypeRepository.findByRoomTypeContainingIgnoreCase(query));
                break;
            case "roomRates":
                BigDecimal queryBigDecimal = new BigDecimal(query);
                roomTypes.addAll(roomTypeRepository.findByRoomRates(queryBigDecimal));
                break;
            case "notes":
                roomTypes.addAll(roomTypeRepository.findByNotesContainingIgnoreCase(query));
                break;
            default:
                // Handle unknown column or do nothing
                break;
        }
        return roomTypes.stream().distinct().toList();
    }

}
