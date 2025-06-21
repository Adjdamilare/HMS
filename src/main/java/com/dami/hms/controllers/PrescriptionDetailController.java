// D:\IdealProjects\HMS\src\main\java\com\dami\hms\controllers\PrescriptionDetailController.java
package com.dami.hms.controllers;

import com.dami.hms.entities.PrescriptionDetail;
import com.dami.hms.repositories.PrescriptionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionDetailController {

    @Autowired
    private PrescriptionDetailRepository prescriptionDetailRepository;

    @GetMapping
    public String listPrescriptions(Model model) {
        List<PrescriptionDetail> prescriptions = prescriptionDetailRepository.findAll();
        model.addAttribute("prescriptions", prescriptions);
        return "prescription/index";
    }

    @GetMapping("/new")
    public String showNewPrescriptionForm(Model model) {
        String nextId = generateNextPrescriptionId();

        PrescriptionDetail prescription = new PrescriptionDetail();
        prescription.setPrescriptionId(nextId);

        model.addAttribute("prescription", prescription);
        return "prescription/new";
    }

    @PostMapping
    public String savePrescription(@ModelAttribute("prescription") PrescriptionDetail prescription) {
        System.out.println("Saving prescription: " + prescription.getPrescriptionId());

        if (prescription.getPrescriptionId() == null || prescription.getPrescriptionId().isEmpty()) {
            prescription.setPrescriptionId(generateNextPrescriptionId());
        }

        prescriptionDetailRepository.save(prescription);
        return "redirect:/prescriptions";
    }

    @GetMapping("/delete/{id}")
    public String deletePrescription(@PathVariable("id") String id) {
        prescriptionDetailRepository.deleteById(id);
        return "redirect:/prescriptions";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        PrescriptionDetail prescription = prescriptionDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid prescription ID: " + id));
        model.addAttribute("prescription", prescription);
        return "prescription/edit";
    }

    private String generateNextPrescriptionId() {
        List<PrescriptionDetail> allPrescriptions = prescriptionDetailRepository.findAll();

        return allPrescriptions.stream()
                .map(p -> p.getPrescriptionId())
                .filter(id -> id != null && id.matches("PRE\\d{3}"))
                .max(Comparator.comparingInt(id -> Integer.parseInt(id.substring(3))))
                .map(lastId -> {
                    int nextNumber = Integer.parseInt(lastId.substring(3)) + 1;
                    return String.format("PRE%03d", nextNumber);
                })
                .orElse("PRE001");
    }
}
