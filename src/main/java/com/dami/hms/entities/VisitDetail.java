package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "visit_details", schema = "hms")
public class VisitDetail {
    @Id
    @Size(max = 255)
    @Column(name = "visit_id", nullable = false)
    private String visitId;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Column(name = "visit_time")
    private LocalTime visitTime;

    @Size(max = 255)
    @Column(name = "doctor_id")
    private String doctorId;

    @Size(max = 255)
    @Column(name = "admission_id")
    private String admissionId;

    @Size(max = 255)
    @Column(name = "patient_id")
    private String patientId;

    @Size(max = 255)
    @Column(name = "prescription_id")
    private String prescriptionId;

}