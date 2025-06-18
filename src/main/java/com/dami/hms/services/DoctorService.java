package com.dami.hms.services;

import com.dami.hms.entities.Doctor;
import com.dami.hms.models.DoctorDto;
import com.dami.hms.repositories.DoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public String generateNextDoctorId() {
        Optional<Doctor> lastDoctorOptional = doctorRepository.findAll(Sort.by(Sort.Direction.DESC, "doctorId")).stream().findFirst();

        if (lastDoctorOptional.isPresent()) {
            String lastDoctorId = lastDoctorOptional.get().getDoctorId();
            int lastNumber = Integer.parseInt(lastDoctorId.substring(2));
            return String.format("DC%03d", lastNumber + 1);
        } else {
            return "DC001"; // First doctor
        }
    }


    public List<String> getAllDoctorsId() {
        return doctorRepository.findAllDoctorIds();
    }

    //M
    public void saveDoctor(Doctor doctor){
        doctorRepository.save(doctor);
    }

    public Doctor getDoctorById(String id) {
        return doctorRepository.findByDoctorId(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public void deleteDoctor(String id) {
        doctorRepository.deleteById(id);
    }

    @Transactional
    public void delete(String doctorId){
        doctorRepository.softDelete(doctorId);
    }


    public List<Doctor> searchDoctors(String query) {
        List<Doctor> doctors = new ArrayList<>();
        doctors.addAll(doctorRepository.findByDoctorIdContainingIgnoreCase(query));
        doctors.addAll(doctorRepository.findByDoctorFnameContainingIgnoreCase(query));
        doctors.addAll(doctorRepository.findByDoctorLnameContainingIgnoreCase(query));
        doctors.addAll(doctorRepository.findByDoctorSexContainingIgnoreCase(query));
        doctors.addAll(doctorRepository.findByDoctorNidContainingIgnoreCase(query));
        doctors.addAll(doctorRepository.findByDoctorHphoneContainingIgnoreCase(query));
        doctors.addAll(doctorRepository.findByDoctorMphoneContainingIgnoreCase(query));
        doctors.addAll(doctorRepository.findByDoctorAddressContainingIgnoreCase(query));
        doctors.addAll(doctorRepository.findByDoctorQualificationContainingIgnoreCase(query));
        doctors.addAll(doctorRepository.findByDoctorSpecializationContainingIgnoreCase(query));
        doctors.addAll(doctorRepository.findByDoctorTypeContainingIgnoreCase(query));
        doctors.addAll(doctorRepository.findByDoctorVchargeGreaterThanEqual(Double.parseDouble(query)));
        doctors.addAll(doctorRepository.findByDoctorCchargeGreaterThanEqual(Double.parseDouble(query)));
        doctors.addAll(doctorRepository.findByDoctorBasicSalGreaterThanEqual(Double.parseDouble(query)));
        return doctors.stream().distinct().toList();
    }

    public List<Doctor> searchDoctorsByColumn(String query, String searchColumn) {
        List<Doctor> doctors = new ArrayList<>();
        switch (searchColumn) {
            case "doctorId":
                doctors.addAll(doctorRepository.findByDoctorIdContainingIgnoreCase(query));
                break;
            case "doctorFname":
                doctors.addAll(doctorRepository.findByDoctorFnameContainingIgnoreCase(query));
                break;
            case "doctorLname":
                doctors.addAll(doctorRepository.findByDoctorLnameContainingIgnoreCase(query));
                break;
            case "doctorSex":
                doctors.addAll(doctorRepository.findByDoctorSexContainingIgnoreCase(query));
                break;
            case "doctorNid":
                doctors.addAll(doctorRepository.findByDoctorNidContainingIgnoreCase(query));
                break;
            case "doctorHphone":
                doctors.addAll(doctorRepository.findByDoctorHphoneContainingIgnoreCase(query));
                break;
            case "doctorMphone":
                doctors.addAll(doctorRepository.findByDoctorMphoneContainingIgnoreCase(query));
                break;
            case "doctorAddress":
                doctors.addAll(doctorRepository.findByDoctorAddressContainingIgnoreCase(query));
                break;
            case "doctorQualification":
                doctors.addAll(doctorRepository.findByDoctorQualificationContainingIgnoreCase(query));
                break;
            case "doctorSpecialization":
                doctors.addAll(doctorRepository.findByDoctorSpecializationContainingIgnoreCase(query));
                break;
            case "doctorType":
                doctors.addAll(doctorRepository.findByDoctorTypeContainingIgnoreCase(query));
                break;
            case "doctorVcharge":
                doctors.addAll(doctorRepository.findByDoctorVchargeGreaterThanEqual(Double.parseDouble(query)));
                break;
            case "doctorCcharge":
                doctors.addAll(doctorRepository.findByDoctorCchargeGreaterThanEqual(Double.parseDouble(query)));
                break;
            case "doctorBasicSal":
                doctors.addAll(doctorRepository.findByDoctorBasicSalGreaterThanEqual(Double.parseDouble(query)));
                break;
            default:
                // Handle unknown column or do nothing
                break;
        }
        return doctors.stream().distinct().toList();
    }

    public Doctor getDoctor(String doctorId) {
        return doctorRepository.findById(doctorId).orElse(null);
    }
}