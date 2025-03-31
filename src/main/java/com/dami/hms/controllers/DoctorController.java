package com.dami.hms.controllers;

import com.dami.hms.entities.Doctor;
import com.dami.hms.models.Column;
import com.dami.hms.models.Field;
import com.dami.hms.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    private List<Column> getDoctorColumns() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("doctorId", "Doctor ID"));
        columns.add(new Column("doctorFname", "First Name"));
        columns.add(new Column("doctorLname", "Last Name"));
        columns.add(new Column("doctorSex", "Sex"));
        columns.add(new Column("doctorNid", "NID"));
        columns.add(new Column("doctorHphone", "Home Phone"));
        columns.add(new Column("doctorMphone", "Mobile Phone"));
        columns.add(new Column("doctorAddress", "Address"));
        columns.add(new Column("doctorQualification", "Qualification"));
        columns.add(new Column("doctorSpecialization", "Specialization"));
        columns.add(new Column("doctorType", "Type"));
        columns.add(new Column("doctorVcharge", "Visiting Charge"));
        columns.add(new Column("doctorCcharge", "Consultation Charge"));
        columns.add(new Column("doctorNotes", "Notes"));
        columns.add(new Column("doctorBasicSal", "Basic Salary"));
        return columns;
    }

    private List<Field> getDoctorFields() {
        List<Field> fields = new ArrayList<>();
        fields.add(new Field("doctorId", "Doctor ID", "text", true));
        fields.add(new Field("doctorFname", "First Name", "text", true));
        fields.add(new Field("doctorLname", "Last Name", "text", true));
        fields.add(new Field("doctorSex", "Sex", "text", true));
        fields.add(new Field("doctorNid", "NID", "text", true));
        fields.add(new Field("doctorHphone", "Home Phone", "text", false));
        fields.add(new Field("doctorMphone", "Mobile Phone", "text", true));
        fields.add(new Field("doctorAddress", "Address", "text", true));
        fields.add(new Field("doctorQualification", "Qualification", "text", true));
        fields.add(new Field("doctorSpecialization", "Specialization", "text", true));
        fields.add(new Field("doctorType", "Type", "text", true));
        fields.add(new Field("doctorVcharge", "Visiting Charge", "number", true));
        fields.add(new Field("doctorCcharge", "Consultation Charge", "number", true));
        fields.add(new Field("doctorNotes", "Notes", "text", false));
        fields.add(new Field("doctorBasicSal", "Basic Salary", "number", true));
        return fields;
    }

    @GetMapping
    public String listDoctors(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();

        model.addAttribute("title", "Doctor Details");
        model.addAttribute("items", doctors);
        model.addAttribute("columns", getDoctorColumns());
        model.addAttribute("entityPath", "doctor");

        return "doctor/index";
    }

    @GetMapping("/refresh")
    public String refresh(){
        return "redirect:/doctor";
    }

    @GetMapping("/new")
    public String newDoctor(Model model) {
        String doctorId = doctorService.generateNextDoctorId();
        model.addAttribute("title", "Doctor");
        model.addAttribute("entityPath", "doctor");
        model.addAttribute("fields", getDoctorFields());
        model.addAttribute("idValue", doctorId);
        return "doctor/new";
    }

    @GetMapping("/search")
    public String searchDoctors(@RequestParam("q") String query, @RequestParam(value = "searchColumn", required = false) String searchColumn, Model model) {
        System.out.println(searchColumn);
        List<Doctor> doctors;
        if (searchColumn == null || searchColumn.isEmpty()) {
            doctors = doctorService.searchDoctors(query);
        } else {
            doctors = doctorService.searchDoctorsByColumn(query, searchColumn);
        }

        model.addAttribute("title", "Doctor Details");
        model.addAttribute("items", doctors);
        model.addAttribute("columns", getDoctorColumns());
        model.addAttribute("entityPath", "doctor");

        return "doctor/index";
    }


    @GetMapping("/back")
    public String back() {
        return "menu";
    }
    @GetMapping("/back/new")
    public String backNew(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();

        model.addAttribute("title", "Doctor Details");
        model.addAttribute("items", doctors);
        model.addAttribute("columns", getDoctorColumns());
        model.addAttribute("entityPath", "doctor");
        return "doctor/index";
    }

    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute Doctor doctor, @RequestParam("doctorId") String doctorId) {
        doctor.setDoctorId(doctorId);
        doctorService.saveDoctor(doctor);
        return "redirect:/doctor";
    }

    @GetMapping("/edit/{id}")
    public String editDoctor(@PathVariable String id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id);
        List<Field> fields = getDoctorFields();
        model.addAttribute("title", "Doctor");
        model.addAttribute("entityPath", "doctor");
        model.addAttribute("fields", fields);
        model.addAttribute("idValue", id);
        model.addAttribute("item", doctor);
        return "doctor/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable String id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctor";
    }
}