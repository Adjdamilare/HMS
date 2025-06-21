// D:\IdealProjects\HMS\src\main\java\com\dami\hms\controllers\OutpatientTreatmentController.java
package com.dami.hms.controllers;

import com.dami.hms.entities.*;
import com.dami.hms.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/treatments")
public class OutpatientTreatmentController {

    @Autowired
    private TreatmentDetailRepository treatmentDetailRepository;

    @Autowired
    private InpatientRepository inpatientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PrescriptionDetailRepository prescriptionDetailRepository;

    @GetMapping
    public String listTreatments(Model model) {
        List<TreatmentDetail> treatments = treatmentDetailRepository.findAll();
        model.addAttribute("treatments", treatments);
        return "treatment/index";
    }

    @GetMapping("/new")
    public String showNewTreatmentForm(Model model) {
        String nextId = generateNextTreatmentId();

        TreatmentDetail treatment = new TreatmentDetail();
        treatment.setTreatmentId(nextId);
        treatment.setTreatmentDate(LocalDate.now());

        model.addAttribute("treatment", treatment);
        model.addAttribute("patients", inpatientRepository.findByStatusOrderByPatientIdAsc((byte) 0));
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("prescriptions", prescriptionDetailRepository.findAll());

        return "treatment/new";
    }

    @PostMapping
    public String saveTreatment(@ModelAttribute("treatment") TreatmentDetail treatment) {
        System.out.println("Saving treatment: " + treatment.getTreatmentId());

        if (treatment.getTreatmentId() == null || treatment.getTreatmentId().isEmpty()) {
            treatment.setTreatmentId(generateNextTreatmentId());
        }

        treatmentDetailRepository.save(treatment);
        return "redirect:/treatments";
    }

    @GetMapping("/delete/{id}")
    public String deleteTreatment(@PathVariable("id") String id) {
        treatmentDetailRepository.deleteById(id);
        return "redirect:/treatments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        TreatmentDetail treatment = treatmentDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid treatment ID: " + id));

        model.addAttribute("treatment", treatment);
        model.addAttribute("patients", inpatientRepository.findByStatusOrderByPatientIdAsc((byte) 0));
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("prescriptions", prescriptionDetailRepository.findAll());

        return "treatment/edit";
    }

    @GetMapping("/back")
    public String backToMenu() {
        return "menu";
    }

    private String generateNextTreatmentId() {
        List<TreatmentDetail> allTreatments = treatmentDetailRepository.findAll();

        return allTreatments.stream()
                .map(t -> t.getTreatmentId())
                .filter(id -> id != null && id.matches("TRT\\d{3}"))
                .max(Comparator.comparingInt(id -> Integer.parseInt(id.substring(3))))
                .map(lastId -> {
                    int nextNumber = Integer.parseInt(lastId.substring(3)) + 1;
                    return String.format("TRT%03d", nextNumber);
                })
                .orElse("TRT001");
    }
}
