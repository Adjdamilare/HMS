package com.dami.hms.controllers;


import com.dami.hms.entities.DoctorScheduleDetail;
import com.dami.hms.entities.ServiceScheduleDetail;
import com.dami.hms.entities.Services;
import com.dami.hms.models.Column;
import com.dami.hms.models.Field;
import com.dami.hms.services.DoctorScheduleService;
import com.dami.hms.services.ServiceScheduleService;
import com.dami.hms.services.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/serviceSchedule")
public class ServiceScheduleController {

    @Autowired
    private ServiceScheduleService serviceScheduleService;

    @Autowired
    private ServicesService servicesService;

    // Renamed method and improved display names
    private List<Column> getServiceScheduleColumns() {
        List<Column> columns = new ArrayList<>();
        // Use the actual field names from DoctorScheduleDetail for the first parameter
        columns.add(new Column("serviceScheduleId", "Schedule ID"));
        columns.add(new Column("serviceId", "Service ID"));
        columns.add(new Column("serviceStarts", "Time Starts"));
        columns.add(new Column("serviceEnds", "Time Ends"));
        columns.add(new Column("serviceAvailDate", "Availability Days"));
        columns.add(new Column("scheduleNotes", "Notes"));
        return columns;
    }

    // Renamed method and corrected field types/requirements
    private List<Field> getServiceScheduleFields() {
        List<Field> fields = new ArrayList<>();
        // Schedule ID is usually not manually entered/edited
        fields.add(new Field("serviceScheduleId", "Schedule ID", "text", true)); // Removed for 'new', maybe hidden/readonly for 'edit'
        fields.add(new Field("serviceId", "Service ID", "text", true)); // Assuming Doctor ID is known/entered
        fields.add(new Field("serviceStarts", "Time Starts", "time", true)); // Use 'time' for LocalTime
        fields.add(new Field("serviceEnds", "Time Ends", "time", true)); // Use 'time' for LocalTime, made required for consistency
        fields.add(new Field("scheduleNotes", "Notes", "textarea", false)); // 'textarea' might be better for notes
        return fields;
    }

    @GetMapping
    public String listServiceSchedules(Model model) {
        List<ServiceScheduleDetail> schedules = serviceScheduleService.getAllServiceSchedules(); // Use correct service and entity
        model.addAttribute("title", "Service Schedule");
        model.addAttribute("items", schedules);
        model.addAttribute("columns", getServiceScheduleColumns());
        model.addAttribute("entityPath", "serviceSchedule");
        return "serviceSchedule/index";
    }

    @GetMapping("/new")
    public String newServiceSchedule(Model model) {
        List<String> allDays = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        String newScheduleId = serviceScheduleService.generateNextServiceScheduleId();
        List<Services> allServices = servicesService.getAllServices(); // Get services

        model.addAttribute("title", "New Service Schedule");
        model.addAttribute("entityPath", "serviceSchedule");
        model.addAttribute("schedule", new ServiceScheduleDetail());
        model.addAttribute("allDays", allDays);
        model.addAttribute("fields", getServiceScheduleFields());
        model.addAttribute("idValue", newScheduleId);
        model.addAttribute("allServices", allServices); // Pass services
        return "serviceSchedule/new";
    }


    @PostMapping("/save")
    public String saveServiceSchedule(@ModelAttribute("schedule") ServiceScheduleDetail schedule,
                                     @RequestParam(value = "selectedDays", required = false) List<String> selectedDays,
                                     RedirectAttributes redirectAttributes) {

        // 1. Process the selected days from the checklist
        if (selectedDays != null && !selectedDays.isEmpty()) {
            // Join the list of selected days into a comma-separated string
            String daysString = String.join(",", selectedDays);
            schedule.setServiceAvailDate(daysString); // Set the string to the entity field
        } else {
            schedule.setServiceAvailDate(""); // Or null, depending on preference/DB constraints
        }
        try {
            serviceScheduleService.createServiceSchedule(schedule);
            // Add success message for feedback after redirect
            redirectAttributes.addFlashAttribute("successMessage", "Doctor schedule created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating doctor schedule: " + e.getMessage());
            return "redirect:/serviceSchedule/new";
        }

        // 4. Redirect to the list view
        return "redirect:/serviceSchedule";
    }

    @GetMapping("/back")
    public String back() {
        return "menu";
    }

    @GetMapping("/back/new")
    public String backNew(Model model) {
        List<ServiceScheduleDetail> schedules = serviceScheduleService.getAllServiceSchedules();

        model.addAttribute("title", "Doctor Schedules"); // Correct title
        model.addAttribute("items", schedules); // Use 'items' to match ServiceController pattern, value is correct list
        model.addAttribute("columns", getServiceScheduleColumns()); // Use correctly named method
        model.addAttribute("entityPath", "serviceSchedule"); // Correct entity path
        return "serviceSchedule/index";
    }

    @GetMapping("/refresh")
    public String refresh(){
        return "redirect:/serviceSchedule";
    }

    @GetMapping("/delete/{id}")
    public String deleteService(@PathVariable String id) {
        serviceScheduleService.delete(id);
        return "redirect:/serviceSchedule";
    }

    //-------------------------search function-------------------------------
    @GetMapping("/search")
    public String searchDoctorSchedules(
            @RequestParam("q") String query,
            @RequestParam(value = "searchColumn", required = false) String searchColumn,
            Model model) {

        List<ServiceScheduleDetail> schedules;
        if (searchColumn == null || searchColumn.isEmpty()) {
            // If no specific column is selected, search across all relevant fields
            schedules = serviceScheduleService.searchServiceSchedules(query);
        } else {
            // Search within the specified column
            schedules = serviceScheduleService.searchServiceSchedulesByColumn(query, searchColumn);
        }

        model.addAttribute("title", "Doctor Schedules");
        model.addAttribute("items", schedules);
        model.addAttribute("columns", getServiceScheduleColumns());
        model.addAttribute("entityPath", "serviceSchedule");
        return "serviceSchedule/index";
    }


    // --- EDIT Functionality ---// for editing the service Schedule detail
    @GetMapping("/edit/{id}")
    public String editServiceSchedule(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            ServiceScheduleDetail schedule = serviceScheduleService.getServiceSchedule(id);
            if (schedule == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Service Schedule not found with ID: " + id);
                return "redirect:/serviceSchedule";
            }

            List<String> allDays = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
            List<String> selectedDays = new ArrayList<>();
            if (schedule.getServiceAvailDate() != null && !schedule.getServiceAvailDate().isEmpty()) {
                selectedDays = Arrays.asList(schedule.getServiceAvailDate().split(","));
            }

            List<Services> allServices = servicesService.getAllServices(); // Get services

            model.addAttribute("title", "Edit Service Schedule");
            model.addAttribute("entityPath", "serviceSchedule");
            model.addAttribute("schedule", schedule);
            model.addAttribute("allDays", allDays);
            model.addAttribute("selectedDays", selectedDays);
            model.addAttribute("fields", getServiceScheduleFields());
            model.addAttribute("isNew", false);
            model.addAttribute("allServices", allServices); // Pass services
            return "serviceSchedule/edit";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error loading schedule for edit: " + e.getMessage());
            return "redirect:/serviceSchedule";
        }
    }



    @PostMapping("/update")
    public String updateServiceSchedule(@ModelAttribute("schedule") ServiceScheduleDetail schedule,
                                        @RequestParam(value = "selectedDays", required = false) List<String> selectedDays,
                                        RedirectAttributes redirectAttributes) {

        // Process selected days (same logic as save)
        if (selectedDays != null && !selectedDays.isEmpty()) {
            String daysString = String.join(",", selectedDays);
            schedule.setServiceAvailDate(daysString);
        } else {
            schedule.setServiceAvailDate(""); // Or null
        }

        try {
            serviceScheduleService.updateServiceSchedule(schedule); // Assumes this method exists in your service
            redirectAttributes.addFlashAttribute("successMessage", "Service schedule updated successfully!");
            return "redirect:/serviceSchedule";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating service schedule: " + e.getMessage());
            // Repopulate model attributes needed for the 'edit' view if redirecting back
            List<String> allDays = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
            redirectAttributes.addFlashAttribute("schedule", schedule); // Pass back the submitted data
            redirectAttributes.addFlashAttribute("allDays", allDays);
            redirectAttributes.addFlashAttribute("selectedDays", selectedDays != null ? selectedDays : Collections.emptyList());
            redirectAttributes.addFlashAttribute("fields", getServiceScheduleFields());
            redirectAttributes.addFlashAttribute("isNew", false);
            // Redirect back to the edit form for the specific ID
            return "redirect:/serviceSchedule/edit/" + schedule.getServiceScheduleId();
        }
    }

}
