// D:\IdealProjects\HMS\src\main\java\com\dami\hms\controllers\MedicineDetailController.java
package com.dami.hms.controllers;

import com.dami.hms.entities.MedicineCategory;
import com.dami.hms.entities.MedicineDetail;
import com.dami.hms.repositories.MedicineCategoryRepository;
import com.dami.hms.repositories.MedicineDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/medicine-details")
public class MedicineDetailController {

    @Autowired
    private MedicineDetailRepository medicineDetailRepository;

    @Autowired
    private MedicineCategoryRepository medicineCategoryRepository;

    @GetMapping
    public String listMedicineDetails(Model model) {
        List<MedicineDetail> details = medicineDetailRepository.findAll();
        model.addAttribute("details", details);
        return "medicineDetails/index";
    }

    @GetMapping("/new")
    public String showNewMedicineDetailForm(Model model) {
        String nextId = generateNextMedicineId();

        MedicineDetail detail = new MedicineDetail();
        detail.setProductId(nextId); // Set the generated ID

        Set<MedicineCategory> categories = medicineCategoryRepository.findAll().stream().collect(java.util.stream.Collectors.toSet());
        model.addAttribute("detail", detail);
        model.addAttribute("categories", categories);

        return "medicineDetails/new";
    }

    @PostMapping
    public String saveMedicineDetail(@ModelAttribute("detail") MedicineDetail detail,
                                     @RequestParam("medicineCategory") String categoryId) {
        System.out.println("Saving medicine detail: " + detail.getProductId() + " - " + detail.getProductName());

        if (detail.getProductId() == null || detail.getProductId().isEmpty()) {
            detail.setProductId(generateNextMedicineId());
        }

        // Fetch the category by ID and set it on the detail
        MedicineCategory category = medicineCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));
        detail.setMedicineCategory(category);

        medicineDetailRepository.save(detail);
        return "redirect:/medicine-details";
    }


    @GetMapping("/back")
    public String backToMenu() {
        return "menu";
    }

    private String generateNextMedicineId() {
        List<MedicineDetail> allDetails = medicineDetailRepository.findAll();

        return allDetails.stream()
                .map(detail -> detail.getProductId())
                .filter(id -> id != null && id.matches("MDP\\d{3}"))
                .max(Comparator.comparingInt(id -> Integer.parseInt(id.substring(3))))
                .map(lastId -> {
                    int nextNumber = Integer.parseInt(lastId.substring(3)) + 1;
                    return String.format("MDP%03d", nextNumber);
                })
                .orElse("MDP001");
    }
}
