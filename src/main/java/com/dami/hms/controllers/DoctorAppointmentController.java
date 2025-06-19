package com.dami.hms.controllers;

import com.dami.hms.entities.*;
import com.dami.hms.models.Column;
import com.dami.hms.repositories.DoctorAppointmentRepository;
import com.dami.hms.repositories.DoctorRepository;
import com.dami.hms.repositories.OutpatientRepository;
import com.dami.hms.services.DoctorScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/doctor-appointments")
public class DoctorAppointmentController {



    @Autowired
    private DoctorAppointmentRepository doctorAppointmentRepository;

    @Autowired
    DoctorScheduleService doctorScheduleService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private OutpatientRepository outpatientRepository;

    @GetMapping
    public String listDoctorAppointments(Model model) {
        List<DoctorAppointment> appointments = doctorAppointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "doctorAppointment/index";
    }

    @GetMapping("/new")
    public String showNewAppointmentForm(Model model) {
        String nextId = generateNextAppointmentId();

        DoctorAppointment appointment = new DoctorAppointment();
        appointment.setAppointmentId(nextId); // Set the generated ID here

        List<Doctor> doctors = doctorRepository.findAll();
        List<Outpatient> outpatients = outpatientRepository.findByActiveOrderByPatientIdAsc((byte) 0);

        List<DoctorScheduleDetail> doctorSchedules = doctorScheduleService.getAllDoctorSchedules();
        for(DoctorScheduleDetail schedule: doctorSchedules){
            schedule.setDoctorId(schedule.getDoctor().getDoctorId());
        }

        model.addAttribute("doctorSchedules", doctorSchedules);
        model.addAttribute("scheduleColumns", getDoctorScheduleColumns());
        model.addAttribute("appointment", appointment);
        model.addAttribute("doctors", doctors);
        model.addAttribute("outpatients", outpatients);

        return "doctorAppointment/new";
    }


    private String generateNextAppointmentId() {
        List<DoctorAppointment> allAppointments = doctorAppointmentRepository.findAll();

        // Filter out IDs that don't match the expected pattern "DOA###"
        return allAppointments.stream()
                .map(app -> app.getAppointmentId())
                .filter(id -> id != null && id.matches("DOA\\d{3}"))
                .max(Comparator.comparingInt(id -> Integer.parseInt(id.substring(3))))
                .map(lastId -> {
                    int nextNumber = Integer.parseInt(lastId.substring(3)) + 1;
                    return String.format("DOA%03d", nextNumber);
                })
                .orElse("DOA001");
    }


    private List<Column> getDoctorScheduleColumns() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("doctorScheduleId", "Schedule ID"));
        columns.add(new Column("doctorId", "Doctor ID"));
        columns.add(new Column("doctorIn", "Time In"));
        columns.add(new Column("doctorOut", "Time Out"));
        columns.add(new Column("doctorAvailDate", "Availability Days"));
        columns.add(new Column("scheduleNotes", "Notes"));
        return columns;
    }


    @PostMapping
    public String createDoctorAppointment(@ModelAttribute("appointment") DoctorAppointment appointment,
                                          RedirectAttributes redirectAttributes) {
        try {
            // Validate required fields
            if (appointment.getDoctorId() == null || appointment.getDoctorId().isEmpty()) {
                throw new IllegalArgumentException("Doctor ID is required.");
            }
            if (appointment.getPatientId() == null || appointment.getPatientId().isEmpty()) {
                throw new IllegalArgumentException("Patient ID is required.");
            }

            // Save appointment
            doctorAppointmentRepository.save(appointment);

            redirectAttributes.addFlashAttribute("successMessage", "Appointment saved successfully!");
            return "redirect:/doctor-appointments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving appointment: " + e.getMessage());
            return "redirect:/doctor-appointments/new";
        }
    }


}


