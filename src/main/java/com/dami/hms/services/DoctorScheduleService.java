package com.dami.hms.services;

import com.dami.hms.entities.Doctor;
import com.dami.hms.entities.DoctorScheduleDetail;
import com.dami.hms.entities.ServiceScheduleDetail;
import com.dami.hms.repositories.DoctorScheduleDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorScheduleService {

    @Autowired
    private DoctorScheduleDetailRepository doctorScheduleDetailRepository;

    public List<DoctorScheduleDetail> getAllDoctorSchedules() {
        return doctorScheduleDetailRepository.findAllActive();
    }

    public String generateNextDoctorScheduleId(){
        Optional<DoctorScheduleDetail> lastScheduleOptional = doctorScheduleDetailRepository.findAll(Sort.by(Sort.Direction.DESC, "doctorScheduleId")).stream().findFirst();

        if (lastScheduleOptional.isPresent()) {
            String lastScheduleId = lastScheduleOptional.get().getDoctorScheduleId();
            int lastNumber = Integer.parseInt(lastScheduleId.substring(2));
            return String.format("DS%03d", lastNumber + 1);
        } else {
            return "DS001"; // First doctor schedule ID
        }
    }

    @Transactional
    public void createDoctorSchedule(DoctorScheduleDetail schedule) {
        if (schedule.getDoctorScheduleId() == null || schedule.getDoctorScheduleId().isEmpty()) {
            schedule.setDoctorScheduleId(generateNextDoctorScheduleId());
        }
        doctorScheduleDetailRepository.save(schedule);
    }

    public DoctorScheduleDetail getDoctorScheduleById(String id) {
        return doctorScheduleDetailRepository.findByDoctorScheduleId(id);
    }


    @Transactional
    public void delete(String id) {
        doctorScheduleDetailRepository.delete(id);
    }

    public void updateDoctorSchedule(DoctorScheduleDetail schedule) {
        doctorScheduleDetailRepository.save(schedule);
    }

    public List<DoctorScheduleDetail> searchDoctorSchedules(String query) {
        return searchDoctorSchedulesByColumn(query,null);
    }

    public DoctorScheduleDetail getDoctorSchedule(String doctorId) {
        return doctorScheduleDetailRepository.findByDoctorScheduleId(doctorId);
    }


    public List<DoctorScheduleDetail> searchDoctorSchedulesByColumn(String query, String searchColumn) {
        if (query == null || query.trim().isEmpty()) {
            return doctorScheduleDetailRepository.findAll();
        }
        query = query.trim().toUpperCase();

        if (searchColumn == null || searchColumn.isEmpty()) {
            //search by all columns
            return doctorScheduleDetailRepository.findByDoctorScheduleIdContainingIgnoreCase(query);
        }

        switch (searchColumn) {
            case "doctorScheduleId":
                return doctorScheduleDetailRepository.findByDoctorScheduleIdContainingIgnoreCase(query);
            case "doctorId":
                return doctorScheduleDetailRepository.findByDoctorIdContainingIgnoreCase(query);
//            case "doctorIn":
//                return doctorScheduleDetailRepository.findByDoctorInContainingIgnoreCase(query);
//            case "doctorOut":
//                return doctorScheduleDetailRepository.findByDoctorOutContainingIgnoreCase(query);
            case "doctorAvailDate":
                return doctorScheduleDetailRepository.findByDoctorAvailDateContainingIgnoreCase(query);
            case "scheduleNotes":
                return doctorScheduleDetailRepository.findByScheduleNotesContainingIgnoreCase(query);
            default:
                // Return all if invalid column name is provided or search by schedule id by default
                return doctorScheduleDetailRepository.findByDoctorScheduleIdContainingIgnoreCase(query);
        }
    }


}