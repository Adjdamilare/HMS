// D:\IdealProjects\HMS\src\main\java\com\dami\hms\entities\TreatmentDetail.java
package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "treatment_details", schema = "hms")
public class TreatmentDetail {
    @Id
    @Size(max = 255)
    @Column(name = "treatment_id", nullable = false)
    private String treatmentId;

    @Size(max = 255)
    @Column(name = "patient_id")
    private String patientId;

    @Size(max = 255)
    @Column(name = "doctor_id")
    private String doctorId;

    @Column(name = "treatment_date")
    private LocalDate treatmentDate;

    @Lob
    @Column(name = "diagnosis")
    private String diagnosis;

    @Lob
    @Column(name = "treatment_notes")
    private String treatmentNotes;

    @Size(max = 255)
    @Column(name = "prescription_id")
    private String prescriptionId;
}
