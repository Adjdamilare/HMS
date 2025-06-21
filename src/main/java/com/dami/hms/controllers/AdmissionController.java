package com.dami.hms.controllers;

import com.dami.hms.entities.AdmissionDetail;
import com.dami.hms.entities.GuardianDetail;
import com.dami.hms.entities.*;
import com.dami.hms.repositories.*;
import com.dami.hms.repositories.GuardianRepositorty;
import com.dami.hms.repositories.InpatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admissions")
public class AdmissionController {


    @Autowired
    private  InpatientRepository inpatientRepository;

    @Autowired
    private GuardianRepositorty guardianRepository;

    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

    @Autowired
    private BedDetailsRepository bedDetailsRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private final AdmissionRepository admissionRepository;

    public AdmissionController(AdmissionRepository admissionRepository) {
        this.admissionRepository = admissionRepository;
    }

    @GetMapping
    public String listAdmissions(Model model) {
        List<AdmissionDetail> admissions = admissionRepository.findAll();
        model.addAttribute("admissions", admissions);
        return "admission/index";
    }

    @GetMapping("/new")
    public String showNewAdmissionForm(Model model) {
        // Generate new admission ID
        String newAdmissionId = generateNextAdmissionId();
        model.addAttribute("admissionDetail", new AdmissionDetail());
        model.addAttribute("idValue", newAdmissionId);

        // Fetch available options
        List<Inpatient> patients = inpatientRepository.findByStatusOrderByPatientIdAsc((byte) 0);
        List<GuardianDetail> guardians = guardianRepository.findAll();
        List<RoomDetail> rooms = roomDetailsRepository.findAllRoomDetails();
        List<BedDetail> beds = bedDetailsRepository.findByStatus((byte) 0);
        List<Doctor> doctors = doctorRepository.findAll();

        model.addAttribute("patients", patients);
        model.addAttribute("guardians", guardians);
        model.addAttribute("rooms", rooms);
        model.addAttribute("beds", beds);
        model.addAttribute("doctors", doctors);

        return "admission/new";
    }

    @PostMapping("/save")
    public String saveAdmission(@ModelAttribute AdmissionDetail admissionDetail,
                                RedirectAttributes redirectAttributes) {
        try {
            // Set generated ID manually if needed or ensure it's set in constructor/form
            admissionDetail.setAdmissionId(generateNextAdmissionId());
            admissionRepository.save(admissionDetail);
            redirectAttributes.addFlashAttribute("successMessage", "Admission saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save admission.");
            return "redirect:/admissions/new";
        }
        return "redirect:/admissions";
    }



    private String generateNextAdmissionId() {
        Optional<AdmissionDetail> lastAdmissionOptional = admissionRepository.findAll(Sort.by(Sort.Direction.DESC, "admissionId")).stream().findFirst();

        if (lastAdmissionOptional.isPresent()) {
            String lastAdmissionId = lastAdmissionOptional.get().getAdmissionId();
            int lastNumber = Integer.parseInt(lastAdmissionId.substring(2)); // Assuming format like AD001
            return String.format("AD%03d", lastNumber + 1);
        } else {
            return "AD001"; // First admission ID
        }
    }

}
