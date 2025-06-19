package com.dami.hms.controllers;

import com.dami.hms.entities.GuardianDetail;
import com.dami.hms.repositories.GuardianRepositorty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/guardians")
public class GuardianController {

    private final GuardianRepositorty guardianRepository;

    public GuardianController(GuardianRepositorty guardianRepository) {
        this.guardianRepository = guardianRepository;
    }

    @GetMapping
    public String listGuardians(Model model) {
        model.addAttribute("guardians", guardianRepository.findAll());
        return "guardian/index";
    }

    @GetMapping("/new")
    public String showNewGuardianForm(Model model) {
        String nextId = generateNextGuardianId();

        GuardianDetail guardian = new GuardianDetail();
        guardian.setGuardianId(nextId);

        model.addAttribute("guardian", guardian);
        return "guardian/new";
    }

    @GetMapping("/delete/{id}")
    public String deleteGuardian(@PathVariable("id") String id) {
        guardianRepository.deleteById(id);
        return "redirect:/guardians";
    }

    @PostMapping
    public String saveGuardian(@ModelAttribute("guardian") GuardianDetail guardian) {
        // Ensure ID is not empty
        if (guardian.getGuardianId() == null || guardian.getGuardianId().isEmpty()) {
            guardian.setGuardianId(generateNextGuardianId());
        }

        guardianRepository.save(guardian);
        return "redirect:/guardians";
    }

    private String generateNextGuardianId() {
        List<GuardianDetail> allGuardians = guardianRepository.findAll();

        // Filter out IDs that don't match the expected pattern "GID###"
        return allGuardians.stream()
                .map(guardian -> guardian.getGuardianId())
                .filter(id -> id != null && id.matches("GID\\d{3}"))
                .max(Comparator.comparingInt(id -> Integer.parseInt(id.substring(3))))
                .map(lastId -> {
                    int nextNumber = Integer.parseInt(lastId.substring(3)) + 1;
                    return String.format("GID%03d", nextNumber);
                })
                .orElse("GID001");
    }
}
