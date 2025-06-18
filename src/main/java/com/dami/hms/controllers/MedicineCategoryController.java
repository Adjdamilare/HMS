// D:\IdealProjects\HMS\src\main\java\com\dami\hms\controllers\MedicineCategoryController.java
package com.dami.hms.controllers;

import com.dami.hms.entities.Inpatient;
import com.dami.hms.entities.MedicineCategory;
import com.dami.hms.repositories.MedicineCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/medicine-categories")
public class MedicineCategoryController {

    @Autowired
    private MedicineCategoryRepository medicineCategoryRepository;

    @GetMapping
    public String listMedicineCategories(Model model) {
        List<MedicineCategory> categories = medicineCategoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "medicineCategory/index";
    }

    @GetMapping("/new")
    public String showNewMedicineCategoryForm(Model model) {
        String nextId = generateNextMedicineCategoryId();

        MedicineCategory category = new MedicineCategory();
        category.setCategoryId(nextId); // Set the generated ID here

        model.addAttribute("category", category); // Add initialized object to model

        return "medicineCategory/new";
    }



    @PostMapping
    public String saveMedicineCategory(@ModelAttribute("category") MedicineCategory category) {
        System.out.println("Saving category: " + category.getCategoryId() + " - " + category.getCategoryName());

        // Ensure ID is not empty
        if (category.getCategoryId() == null || category.getCategoryId().isEmpty()) {
            category.setCategoryId(generateNextMedicineCategoryId());
        }

        medicineCategoryRepository.save(category);
        return "redirect:/medicine-categories";
    }



//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable("id") String id, Model model) {
//
//    }

    @GetMapping("/delete/{id}")
    public String deleteMedicineCategory(@PathVariable("id") String id) {
        medicineCategoryRepository.deleteById(id);
        return "redirect:/medicine-categories";
    }


    @GetMapping("/back")
    public String backToMenu() {
        return "menu";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        MedicineCategory category = medicineCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid medicine category ID: " + id));
        model.addAttribute("category", category);
        return "medicineCategory/edit";
    }


    private String generateNextMedicineCategoryId() {
        List<MedicineCategory> allCategories = medicineCategoryRepository.findAll();

        // Filter out IDs that don't match the expected pattern "CAT###"
        return allCategories.stream()
                .map(cat -> cat.getCategoryId())
                .filter(id -> id != null && id.matches("CAT\\d{3}"))
                .max(Comparator.comparingInt(id -> Integer.parseInt(id.substring(3))))
                .map(lastId -> {
                    int nextNumber = Integer.parseInt(lastId.substring(3)) + 1;
                    return String.format("CAT%03d", nextNumber);
                })
                .orElse("CAT001");
    }

}
