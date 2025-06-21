// D:\IdealProjects\HMS\src\main\java\com\dami\hms\controllers\VisitController.java
package com.dami.hms.controllers;

import com.dami.hms.entities.Doctor;
import com.dami.hms.entities.Inpatient;
import com.dami.hms.entities.PrescriptionDetail;
import com.dami.hms.entities.VisitDetail;
import com.dami.hms.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/visits")
public class VisitController {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private InpatientRepository inpatientRepository;

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private PrescriptionDetailRepository prescriptionDetailRepository;

    @GetMapping
    public String listVisits(Model model) {
        List<VisitDetail> visits = visitRepository.findAll();
        model.addAttribute("visits", visits);
        return "visit/index";
    }

    @GetMapping("/new")
    public String showNewVisitForm(Model model) {
        String nextId = generateNextVisitId();

        VisitDetail visit = new VisitDetail();
        visit.setVisitId(nextId);

        model.addAttribute("visit", visit);
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("patients", inpatientRepository.findByStatusOrderByPatientIdAsc((byte) 0));
        model.addAttribute("admissions", admissionRepository.findAll());
        model.addAttribute("prescriptions", prescriptionDetailRepository.findAll());

        return "visit/new";
    }

    @PostMapping
    public String saveVisit(@ModelAttribute("visit") VisitDetail visit) {
        System.out.println("Saving visit: " + visit.getVisitId());

        if (visit.getVisitId() == null || visit.getVisitId().isEmpty()) {
            visit.setVisitId(generateNextVisitId());
        }

        visitRepository.save(visit);
        return "redirect:/visits";
    }

    @GetMapping("/delete/{id}")
    public String deleteVisit(@PathVariable("id") String id) {
        visitRepository.deleteById(id);
        return "redirect:/visits";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        VisitDetail visit = visitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid visit ID: " + id));

        model.addAttribute("visit", visit);
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("patients", inpatientRepository.findByStatusOrderByPatientIdAsc((byte) 0));
        model.addAttribute("admissions", admissionRepository.findAll());
        model.addAttribute("prescriptions", prescriptionDetailRepository.findAll());

        return "visit/edit";
    }

    @GetMapping("/back")
    public String backToMenu() {
        return "menu";
    }

    private String generateNextVisitId() {
        List<VisitDetail> allVisits = visitRepository.findAll();

        return allVisits.stream()
                .map(v -> v.getVisitId())
                .filter(id -> id != null && id.matches("VIS\\d{3}"))
                .max(Comparator.comparingInt(id -> Integer.parseInt(id.substring(3))))
                .map(lastId -> {
                    int nextNumber = Integer.parseInt(lastId.substring(3)) + 1;
                    return String.format("VIS%03d", nextNumber);
                })
                .orElse("VIS001");
    }
}
