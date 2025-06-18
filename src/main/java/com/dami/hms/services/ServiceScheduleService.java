package com.dami.hms.services;

import com.dami.hms.entities.ServiceScheduleDetail;
import com.dami.hms.repositories.ServiceScheduleDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ServiceScheduleService {

    @Autowired
    private ServiceScheduleDetailRepository serviceScheduleDetailRepository;

    public List<ServiceScheduleDetail> getAllServiceSchedules() {
        return serviceScheduleDetailRepository.findAllActive();
    }

    public String generateNextServiceScheduleId() {
        Optional<ServiceScheduleDetail> lastScheduleOptional = serviceScheduleDetailRepository.findAll(Sort.by(Sort.Direction.DESC, "serviceScheduleId")).stream().findFirst();

        if (lastScheduleOptional.isPresent()) {
            String lastScheduleId = lastScheduleOptional.get().getServiceScheduleId();
            int lastNumber = Integer.parseInt(lastScheduleId.substring(2));
            return String.format("SS%03d", lastNumber + 1);
        } else {
            return "SS001"; // First service schedule ID
        }
    }

    @Transactional
    public void createServiceSchedule(ServiceScheduleDetail schedule) {
        if (schedule.getServiceScheduleId() == null || schedule.getServiceScheduleId().isEmpty()) {
            schedule.setServiceScheduleId(generateNextServiceScheduleId());
        }
        serviceScheduleDetailRepository.save(schedule);
    }

    @Transactional
    public void updateServiceSchedule(ServiceScheduleDetail schedule) {
        serviceScheduleDetailRepository.save(schedule);
    }

    public Optional<ServiceScheduleDetail> getServiceScheduleById(String id) {
        return serviceScheduleDetailRepository.findById(id);
    }

    public ServiceScheduleDetail getServiceSchedule(String id) {
        return serviceScheduleDetailRepository.findByServiceScheduleId(id);
    }

    @Transactional
    public void delete(String id) {
        serviceScheduleDetailRepository.delete(id);
    }


    public List<ServiceScheduleDetail> searchServiceSchedules(String query) {
        return searchServiceSchedulesByColumn(query, null);
    }

    public List<ServiceScheduleDetail> searchServiceSchedulesByColumn(String query, String searchColumn) {
        if (query == null || query.trim().isEmpty()) {
            return serviceScheduleDetailRepository.findAll();
        }
        query = query.trim().toUpperCase();

        if (searchColumn == null || searchColumn.isEmpty()) {
            return serviceScheduleDetailRepository.findAll();
        }

        return switch (searchColumn) {
            case "serviceScheduleId" ->
                    serviceScheduleDetailRepository.findByServiceScheduleIdContainingIgnoreCase(query);
            case "serviceId" -> serviceScheduleDetailRepository.findByServiceIdContainingIgnoreCase(query);
//            case "serviceStarts":
//                return serviceScheduleDetailRepository.findByServiceStartsContainingIgnoreCase(query);
//            case "serviceEnds":
//                return serviceScheduleDetailRepository.findByServiceEndsContainingIgnoreCase(query);
            case "scheduleNotes" -> serviceScheduleDetailRepository.findByScheduleNotesContainingIgnoreCase(query);
            case "serviceAvailDate" ->
                    serviceScheduleDetailRepository.findByServiceAvailDateContainingIgnoreCase(query);
            default -> serviceScheduleDetailRepository.findAll();
        };
    }
}
