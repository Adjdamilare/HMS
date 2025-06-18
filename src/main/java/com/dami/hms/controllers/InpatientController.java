package com.dami.hms.controllers;

import com.dami.hms.entities.Inpatient;
import com.dami.hms.repositories.InpatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class InpatientController {

    @Autowired
    private InpatientRepository inpatientRepository;

    @GetMapping("/inpatients")
    public String listInpatients(Model model) {
        // Get only active inpatients (status = 0)
        model.addAttribute("inpatients", inpatientRepository.findByStatusOrderByPatientIdAsc((byte) 0));
        return "inPatient/index";
    }

    @GetMapping("/inpatients/new")
    public String showNewInpatientForm(Model model) {
        Inpatient inpatient = new Inpatient();
        String nextId = generateNextInpatientId();
        inpatient.setPatientId(nextId);
        model.addAttribute("inpatient", inpatient);
        return "inPatient/new";
    }

    @PostMapping("/inpatients")
    public String saveInpatient(@ModelAttribute("inpatient") Inpatient inpatient) {
        inpatient.setStatus((byte) 0); // Set active by default
        inpatientRepository.save(inpatient);
        return "redirect:/inpatients";
    }

    @GetMapping("/inpatients/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        Optional<Inpatient> inpatientOptional = inpatientRepository.findByPatientIdAndStatus(id, (byte) 0);
        if (inpatientOptional.isPresent()) {
            model.addAttribute("inpatient", inpatientOptional.get());
            return "inPatient/edit";
        }
        return "redirect:/inpatients";
    }

    @GetMapping("/inpatients/back")
    public String backToMenu() {
        return "menu";
    }

    @GetMapping("/inpatients/delete/{id}")
    public String softDeleteInpatient(@PathVariable("id") String id) {
        Optional<Inpatient> inpatientOptional = inpatientRepository.findByPatientIdAndStatus(id, (byte) 0);
        if (inpatientOptional.isPresent()) {
            Inpatient inpatient = inpatientOptional.get();
            inpatient.setStatus((byte) 1); // Soft delete by setting status to 1
            inpatientRepository.save(inpatient);
        }
        return "redirect:/inpatients";
    }

    private String generateNextInpatientId() {
        Optional<Inpatient> lastInpatientOptional = inpatientRepository.findByStatusOrderByPatientIdAsc((byte) 0).stream().findFirst();

        if (lastInpatientOptional.isPresent()) {
            String lastInpatientId = lastInpatientOptional.get().getPatientId();
            int lastNumber = Integer.parseInt(lastInpatientId.substring(2));
            return String.format("IP%03d", lastNumber + 1);
        } else {
            return "IP001"; // First Patient
        }
    }
}
