package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prescription_details", schema = "hms")
public class PrescriptionDetail {
    @Id
    @Size(max = 255)
    @Column(name = "prescription_id", nullable = false)
    private String prescriptionId;

    @Size(max = 255)
    @Column(name = "medicine_service_id")
    private String medicineServiceId;

    @Lob
    @Column(name = "frequency")
    private String frequency;

    @Column(name = "no_of_days")
    private Integer noOfDays;

}