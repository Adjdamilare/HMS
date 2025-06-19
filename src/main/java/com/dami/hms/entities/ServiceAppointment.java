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
@Table(name = "service_appointment", schema = "hms")
public class ServiceAppointment {
    @Id
    @Size(max = 255)
    @Column(name = "appointment_id", nullable = false)
    private String appointmentId;

    @Size(max = 255)
    @Column(name = "patient_id")
    private String patientId;

    @Size(max = 255)
    @Column(name = "hospital_service_id")
    private String hospitalServiceId;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "appointment_time")
    private LocalTime appointmentTime;

}