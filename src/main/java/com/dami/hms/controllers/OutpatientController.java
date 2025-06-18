package com.dami.hms.controllers;

import com.dami.hms.entities.Doctor;
import com.dami.hms.entities.Outpatient;
import com.dami.hms.repositories.OutpatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class OutpatientController {

    @Autowired
    private OutpatientRepository outpatientRepository;


    @GetMapping("/outpatients")
    public String listOutpatients(Model model) {
        // Get only active outpatients (active = 0)
        model.addAttribute("outpatients", outpatientRepository.findByActiveOrderByPatientIdAsc((byte) 0));
        return "outPatient/index";
    }

    @GetMapping("/back")
    public String backToMenu() {
        return "menu";
    }

    @GetMapping("/outpatients/new")
    public String showNewOutpatientForm(Model model) {
        Outpatient outpatient = new Outpatient();
        String nextId = generateNextOutPatientId();
        outpatient.setPatientId(nextId);
        model.addAttribute("outpatient", outpatient);
        return "outPatient/new";
    }

    @PostMapping("/outpatients")
    public String saveOutpatient(@ModelAttribute("outpatient") Outpatient outpatient) {
        outpatient.setActive((byte) 0); // Set active by default
        outpatientRepository.save(outpatient);
        return "redirect:/outpatients";
    }

    @GetMapping("/outpatients/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        Optional<Outpatient> outpatientOptional = outpatientRepository.findById(id);
        if (outpatientOptional.isPresent()) {
            model.addAttribute("outpatient", outpatientOptional.get());
            return "outPatient/edit";
        }
        return "redirect:/outpatients";
    }

    public String generateNextOutPatientId() {
        Optional<Outpatient> lastOutpatientOptional = outpatientRepository.findAll(Sort.by(Sort.Direction.DESC, "patientId")).stream().findFirst();

        if (lastOutpatientOptional.isPresent()) {
            String lastOutpatientId = lastOutpatientOptional.get().getPatientId();
            int lastNumber = Integer.parseInt(lastOutpatientId.substring(2));
            return String.format("OP%03d", lastNumber + 1);
        } else {
            return "OP001"; // First Patient
        }
    }

    @GetMapping("/outpatients/delete/{id}")
    public String softDeleteOutpatient(@PathVariable("id") String id) {
        Optional<Outpatient> outpatientOptional = outpatientRepository.findById(id);
        if (outpatientOptional.isPresent()) {
            Outpatient outpatient = outpatientOptional.get();
            outpatient.setActive((byte) 1); // Soft delete by setting active to 1
            outpatientRepository.save(outpatient);
        }
        return "redirect:/outpatients";
    }


}
