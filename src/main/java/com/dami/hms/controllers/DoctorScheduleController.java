package com.dami.hms.controllers;

import com.dami.hms.entities.DoctorScheduleDetail; // Import correct entity
import com.dami.hms.entities.ServiceScheduleDetail;
import com.dami.hms.models.Column;
import com.dami.hms.models.Field;
import com.dami.hms.services.DoctorScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // For flash messages

import java.util.*;

@Controller
@RequestMapping("/doctorSchedule") // Correct base path
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleService doctorScheduleService; // Correct service injected

    // Renamed method and improved display names
    private List<Column> getDoctorScheduleColumns() {
        List<Column> columns = new ArrayList<>();
        // Use the actual field names from DoctorScheduleDetail for the first parameter
        columns.add(new Column("doctorScheduleId", "Schedule ID"));
        columns.add(new Column("doctorId", "Doctor ID"));
        columns.add(new Column("doctorIn", "Time In"));
        columns.add(new Column("doctorOut", "Time Out"));
        columns.add(new Column("doctorAvailDate", "Availability Days"));
        columns.add(new Column("scheduleNotes", "Notes"));
        return columns;
    }

    // Renamed method and corrected field types/requirements
    private List<Field> getDoctorScheduleFields() {
        List<Field> fields = new ArrayList<>();
        // Schedule ID is usually not manually entered/edited
        fields.add(new Field("doctorScheduleId", "Schedule ID", "text", true)); // Removed for 'new', maybe hidden/readonly for 'edit'
        fields.add(new Field("doctorId", "Doctor ID", "text", true)); // Assuming Doctor ID is known/entered
        fields.add(new Field("doctorIn", "Time In", "time", true)); // Use 'time' for LocalTime
        fields.add(new Field("doctorOut", "Time Out", "time", true)); // Use 'time' for LocalTime, made required for consistency
        fields.add(new Field("scheduleNotes", "Notes", "textarea", false)); // 'textarea' might be better for notes
        return fields;
    }

    // Renamed method, corrected service call, model attributes, and return view
    @GetMapping
    public String listDoctorSchedules(Model model) {
        List<DoctorScheduleDetail> schedules = doctorScheduleService.getAllDoctorSchedules(); // Use correct service and entity
        model.addAttribute("title", "Doctor Schedules"); // Correct title
        model.addAttribute("items", schedules); // Use 'items' to match ServiceController pattern, value is correct list
        model.addAttribute("columns", getDoctorScheduleColumns()); // Use correctly named method
        model.addAttribute("entityPath", "doctorSchedule"); // Correct entity path
        return "doctorSchedule/index"; // Correct vi
    }

    @GetMapping("/new")
    public String newDoctorSchedule(Model model) {
        List<String> allDays = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"); // All days of the week
        String newScheduleId = doctorScheduleService.generateNextDoctorScheduleId();
        List<Field> fields = new ArrayList<>();
        fields.add(new Field("doctorScheduleId", "Schedule ID", "text", true));
        fields.add(new Field("doctorId", "Doctor ID", "text", true));
        fields.add(new Field("doctorIn", "Time In", "time", true));
        fields.add(new Field("doctorOut", "Time Out", "time", true));
        fields.add(new Field("scheduleNotes", "Notes", "textarea", false));
        model.addAttribute("title", "New Doctor Schedule");
        model.addAttribute("entityPath", "doctorSchedule");
        model.addAttribute("schedule", new DoctorScheduleDetail()); // Add an empty schedule object
        model.addAttribute("allDays", allDays);
        model.addAttribute("fields",fields);
        model.addAttribute("idValue", newScheduleId);
        return "doctorSchedule/new";
    }

    // --- START: POST Mapping for /save ---
    @PostMapping("/save")
    public String saveDoctorSchedule(@ModelAttribute("schedule") DoctorScheduleDetail schedule,
                                     @RequestParam(value = "selectedDays", required = false) List<String> selectedDays,
                                     RedirectAttributes redirectAttributes) {

        // 1. Process the selected days from the checklist
        if (selectedDays != null && !selectedDays.isEmpty()) {
            // Join the list of selected days into a comma-separated string
            String daysString = String.join(",", selectedDays);
            schedule.setDoctorAvailDate(daysString); // Set the string to the entity field
        } else {
            schedule.setDoctorAvailDate(""); // Or null, depending on preference/DB constraints
        }
        try {
            doctorScheduleService.createDoctorSchedule(schedule);
            // Add success message for feedback after redirect
            redirectAttributes.addFlashAttribute("successMessage", "Doctor schedule created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating doctor schedule: " + e.getMessage());
            return "redirect:/doctorSchedule/new";
        }

        // 4. Redirect to the list view
        return "redirect:/doctorSchedule";
    }

    @GetMapping("/back")
    public String back() {
        return "menu";
    }

    @GetMapping("/back/new")
    public String backNew(Model model) {
        List<DoctorScheduleDetail> schedules = doctorScheduleService.getAllDoctorSchedules();

        model.addAttribute("title", "Doctor Schedules"); // Correct title
        model.addAttribute("items", schedules); // Use 'items' to match ServiceController pattern, value is correct list
        model.addAttribute("columns", getDoctorScheduleColumns()); // Use correctly named method
        model.addAttribute("entityPath", "doctorSchedule"); // Correct entity path
        return "doctor/index";
    }

    @GetMapping("/refresh")
    public String refresh(){
        return "redirect:/doctorSchedule";
    }

    @GetMapping("/delete/{id}")
    public String deleteService(@PathVariable String id) {
        doctorScheduleService.delete(id);
        return "redirect:/doctorSchedule";
    }

    //-------------------------search function-------------------------------
    @GetMapping("/search")
    public String searchDoctorSchedules(
            @RequestParam("q") String query,
            @RequestParam(value = "searchColumn", required = false) String searchColumn,
            Model model) {

        List<DoctorScheduleDetail> schedules;
        if (searchColumn == null || searchColumn.isEmpty()) {
            // If no specific column is selected, search across all relevant fields
            schedules = doctorScheduleService.searchDoctorSchedules(query);
        } else {
            // Search within the specified column
            schedules = doctorScheduleService.searchDoctorSchedulesByColumn(query, searchColumn);
        }

        model.addAttribute("title", "Doctor Schedules");
        model.addAttribute("items", schedules);
        model.addAttribute("columns", getDoctorScheduleColumns());
        model.addAttribute("entityPath", "doctorSchedule");
        return "doctorSchedule/index";
    }

    @GetMapping("/edit/{id}")
    public String editDoctorSchedule(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<DoctorScheduleDetail> scheduleOptional = doctorScheduleService.getDoctorScheduleById(id);
            if (scheduleOptional.isEmpty()) {
                // Or throw new EntityNotFoundException("Service Schedule not found with ID: " + id);
                redirectAttributes.addFlashAttribute("errorMessage", "Doctor Schedule not found with ID: " + id);
                return "redirect:/doctorSchedule";
            }

            DoctorScheduleDetail schedule = scheduleOptional.get();

            List<String> allDays = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
            List<String> selectedDays = new ArrayList<>();
            if (schedule.getDoctorAvailDate() != null && !schedule.getDoctorAvailDate().isEmpty()) {
                selectedDays = Arrays.asList(schedule.getDoctorAvailDate().split(","));
            }

            model.addAttribute("title", "Edit Doctor Schedule");
            model.addAttribute("entityPath", "doctorSchedule");
            model.addAttribute("schedule", schedule); // The existing schedule object
            model.addAttribute("allDays", allDays);
            model.addAttribute("selectedDays", selectedDays); // Pre-selected days
            model.addAttribute("fields", getDoctorScheduleFields()); // Use correct method name
            model.addAttribute("isNew", false); // Flag for edit mode
            return "doctorSchedule/edit"; // Path to the new edit template

        } catch (Exception e) { // Catch potential exceptions from the service layer
            redirectAttributes.addFlashAttribute("errorMessage", "Error loading schedule for edit: " + e.getMessage());
            System.out.println("Error loading schedule for edit: " + e.getMessage());
            return "redirect:/doctorSchedule";
        }
    }

    @PostMapping("/update")
    public String updateDoctorSchedule(@ModelAttribute("schedule") DoctorScheduleDetail schedule,
                                        @RequestParam(value = "selectedDays", required = false) List<String> selectedDays,
                                        RedirectAttributes redirectAttributes) {

        // Process selected days (same logic as save)
        if (selectedDays != null && !selectedDays.isEmpty()) {
            String daysString = String.join(",", selectedDays);
            schedule.setDoctorAvailDate(daysString);
        } else {
            schedule.setDoctorAvailDate(""); // Or null
        }

        try {
            doctorScheduleService.updateDoctorSchedule(schedule); // Assumes this method exists in your service
            redirectAttributes.addFlashAttribute("successMessage", "Service schedule updated successfully!");
            return "redirect:/doctorSchedule";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating service schedule: " + e.getMessage());
            // Repopulate model attributes needed for the 'edit' view if redirecting back
            List<String> allDays = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
            redirectAttributes.addFlashAttribute("schedule", schedule); // Pass back the submitted data
            redirectAttributes.addFlashAttribute("allDays", allDays);
            redirectAttributes.addFlashAttribute("selectedDays", selectedDays != null ? selectedDays : Collections.emptyList());
            redirectAttributes.addFlashAttribute("fields", getDoctorScheduleFields());
            redirectAttributes.addFlashAttribute("isNew", false);
            // Redirect back to the edit form for the specific ID
            return "redirect:/doctorSchedule/edit/" + schedule.getDoctorScheduleId();
        }
    }

}