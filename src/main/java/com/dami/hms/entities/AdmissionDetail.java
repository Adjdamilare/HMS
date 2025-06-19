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
@Table(name = "admission_details", schema = "hms")
public class AdmissionDetail {
    @Id
    @Size(max = 255)
    @Column(name = "admission_id", nullable = false)
    private String admissionId;

    @Size(max = 255)
    @Column(name = "patient_id")
    private String patientId;

    @Size(max = 255)
    @Column(name = "guardian_id")
    private String guardianId;

    @Size(max = 255)
    @Column(name = "room_ward_id")
    private String roomWardId;

    @Size(max = 255)
    @Column(name = "bed_id")
    private String bedId;

    @Size(max = 255)
    @Column(name = "Ref_doctor")
    private String refDoctor;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @Lob
    @Column(name = "emergency")
    private String emergency;

    @Column(name = "admission_time")
    private LocalTime admissionTime;

}