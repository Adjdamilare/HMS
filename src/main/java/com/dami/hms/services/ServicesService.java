package com.dami.hms.services;

import com.dami.hms.entities.Doctor;
import com.dami.hms.entities.Services;
import com.dami.hms.repositories.ServiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServicesService {

    @Autowired
    private ServiceRepository serviceRepository;

    public String generateNextServiceId() {
        Optional<Services> lastServiceOptional = serviceRepository.findAll(Sort.by(Sort.Direction.DESC, "channelServiceId")).stream().findFirst();
        if (lastServiceOptional.isPresent()) {
            String lastServiceId = lastServiceOptional.get().getChannelServiceId();
            int lastNumber = Integer.parseInt(lastServiceId.substring(2));
            return String.format("SC%03d", lastNumber + 1);
        } else {
            return "SC001"; // First doctor
        }
    }

    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }

    public Services getServiceById(String id) {
        return serviceRepository.findById(id).orElse(null);
    }

    public Services saveService(Services service) {
        return serviceRepository.save(service);
    }

    public void deleteService(String id) {
        serviceRepository.deleteById(id);
    }

    public List<Services> searchServices(String query) {
        return serviceRepository.findByChannelServiceIdContainingIgnoreCaseOrChannelServiceContainingIgnoreCaseOrServiceNotesContainingIgnoreCase(query, query, query);
    }

    public List<Services> searchServicesByColumn(String query, String searchColumn) {
        switch (searchColumn) {
            case "channelServiceId":
                return serviceRepository.findByChannelServiceIdContainingIgnoreCase(query);
            case "channelService":
                return serviceRepository.findByChannelServiceContainingIgnoreCase(query);
            case "chargeForService":
                try {
                    Double charge = Double.parseDouble(query);
                    return serviceRepository.findByChargeForServiceGreaterThanEqual(charge);
                } catch (NumberFormatException e) {
                    return List.of();
                }
            case "serviceNotes":
                return serviceRepository.findByServiceNotesContainingIgnoreCase(query);
            default:
                return List.of();
        }
    }

    @Transactional
    public void delete(String channelServiceId){
        serviceRepository.softDelete(channelServiceId);
    }
}