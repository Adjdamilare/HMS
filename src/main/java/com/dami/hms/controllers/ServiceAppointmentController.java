package com.dami.hms.controllers;

import com.dami.hms.entities.*;
import com.dami.hms.models.Column;
import com.dami.hms.repositories.*;
import com.dami.hms.services.DoctorScheduleService;
import com.dami.hms.services.ServiceScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/service-appointments")
public class ServiceAppointmentController {


    @Autowired
    private ServiceAppointmentRepository serviceAppointmentRepository;

    @Autowired
    ServiceScheduleService serviceScheduleService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private OutpatientRepository outpatientRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping
    public String listServiceAppointments(Model model) {
        List<ServiceAppointment> appointments = serviceAppointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "serviceAppointment/index";
    }

    @GetMapping("/new")
    public String showNewAppointmentForm(Model model) {
        String nextId = generateNextAppointmentId();

        ServiceAppointment appointment = new ServiceAppointment();
        appointment.setAppointmentId(nextId);

        List<Services> services = serviceRepository.findAll();
        List<Outpatient> outpatients = outpatientRepository.findByActiveOrderByPatientIdAsc((byte) 0);

        List<ServiceScheduleDetail> serviceSchedules = serviceScheduleService.getAllServiceSchedules();
        for(ServiceScheduleDetail schedule: serviceSchedules){
            schedule.setServiceId(schedule.getService().getChannelServiceId());
        }

        model.addAttribute("serviceSchedules", serviceSchedules);
        model.addAttribute("scheduleColumns", getServiceScheduleColumns());
        model.addAttribute("appointment", appointment);
        model.addAttribute("services", services);
        model.addAttribute("outpatients", outpatients);

        return "serviceAppointment/new";
    }


    private String generateNextAppointmentId() {
        List<ServiceAppointment> allAppointments = serviceAppointmentRepository.findAll();

        // Filter out IDs that don't match the expected pattern "DOA###"
        return allAppointments.stream()
                .map(app -> app.getAppointmentId())
                .filter(id -> id != null && id.matches("SOA\\d{3}"))
                .max(Comparator.comparingInt(id -> Integer.parseInt(id.substring(3))))
                .map(lastId -> {
                    int nextNumber = Integer.parseInt(lastId.substring(3)) + 1;
                    return String.format("SOA%03d", nextNumber);
                })
                .orElse("SOA001");
    }
//
//
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
//
//
    @PostMapping
    public String createDoctorAppointment(@ModelAttribute("appointment") ServiceAppointment appointment,
                                          RedirectAttributes redirectAttributes) {
        try {
            // Validate required fields
            if (appointment.getHospitalServiceId() == null || appointment.getHospitalServiceId().isEmpty()) {
                throw new IllegalArgumentException("Service ID is required.");
            }
            if (appointment.getPatientId() == null || appointment.getPatientId().isEmpty()) {
                throw new IllegalArgumentException("Patient ID is required.");
            }

            // Save appointment
            serviceAppointmentRepository.save(appointment);

            redirectAttributes.addFlashAttribute("successMessage", "Appointment saved successfully!");
            return "redirect:/service-appointments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving appointment: " + e.getMessage());
            return "redirect:/service-appointments/new";
        }
    }



    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        List<ServiceAppointment> appointments = serviceAppointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        serviceAppointmentRepository.deleteById(id);
        return "redirect:/service-appointments";
    }
////

}


