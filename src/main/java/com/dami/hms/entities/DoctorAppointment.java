package com.dami.hms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "doctor_appointment", schema = "hms")
public class DoctorAppointment {
    @Id
    @Size(max = 255)
    @Column(name = "appointment_id", nullable = false)
    private String appointmentId;

    @Size(max = 255)
    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "appointment_time")
    private LocalTime appointmentTime;

    @Size(max = 255)
    @Column(name = "doctor_id")
    private String doctorId;

}