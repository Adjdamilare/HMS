package com.dami.hms.services;


import com.dami.hms.entities.RoomDetail;
import com.dami.hms.entities.WardDetail;
import com.dami.hms.repositories.RoomDetailsRepository;
import com.dami.hms.repositories.RoomTypeRepository;
import com.dami.hms.repositories.WardDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WardDetailService {
    @Autowired
    private WardDetailRepository wardDetailRepository;


    public String generateNextWardId() {
        Optional<WardDetail> lastWardOptional = wardDetailRepository.findAll(Sort.by(Sort.Direction.DESC, "wardId")).stream().findFirst();

        if (lastWardOptional.isPresent()) {
            String lastWardId = lastWardOptional.get().getWardId();
            int lastNumber = Integer.parseInt(lastWardId.substring(2));
            return String.format("WW%03d", lastNumber + 1);
        } else {
            return "WW001"; // First ward ID if no ward exists
        }
    }

    //this is for roomDetails
    public List<WardDetail> getWarmDetails() {
        return wardDetailRepository.findAllWardDetails();
    }
    public void saveWardDetail(WardDetail wardDetail) {
        wardDetailRepository.save(wardDetail);
    }

    @Transactional
    public void deleteWardDetail(String wardId) {
        wardDetailRepository.deleteByWardId(wardId);
    }
    public WardDetail getWardDetailById(String wardId) {
        return wardDetailRepository.findByWardId(wardId);
    }
    public List<WardDetail> searchWardDetailByColumn(String query, String searchColumn) {
        List<WardDetail> wardDetails = new ArrayList<>();
        switch (searchColumn) {
            case "wardId":
                wardDetails.addAll(wardDetailRepository.findByWardIdContainingIgnoreCase(query));
                break;
            case "wardName":
                wardDetails.addAll(wardDetailRepository.findByWardNameContainingIgnoreCase(query));
                break;
            case "wardRate":
                BigDecimal queryBigDecimal = new BigDecimal(query);
                wardDetails.addAll(wardDetailRepository.findByWardRateGreaterThanEqual(queryBigDecimal));
                break;
            case "wardDesc":
                wardDetails.addAll(wardDetailRepository.findByWardDescContainingIgnoreCase(query));
                break;
            default:
                // Handle unknown column or do nothing
                break;
        }
        return wardDetails.stream().distinct().toList();
    }

}
