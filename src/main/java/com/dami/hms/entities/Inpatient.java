package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "inpatient_details", schema = "hms")
public class Inpatient {
    @Id
    @Size(max = 255)
    @Column(name = "Patient_ID", nullable = false)
    private String patientId;

    @NotNull
    @Lob
    @Column(name = "Patient_FName", nullable = false)
    private String patientFname;

    @NotNull
    @Lob
    @Column(name = "Patient_LName", nullable = false)
    private String patientLname;

    @Column(name = "Patient_DOB")
    private LocalDate patientDob;

    @Lob
    @Column(name = "Patient_Sex")
    private String patientSex;

    @Lob
    @Column(name = "Patient_NID")
    private String patientNid;

    @Lob
    @Column(name = "Patient_HPhone")
    private String patientHphone;

    @NotNull
    @Lob
    @Column(name = "Patient_MPhone", nullable = false)
    private String patientMphone;

    @Lob
    @Column(name = "Patient_Address")
    private String patientAddress;

    @Column(name = "Patient_Height", precision = 5, scale = 2)
    private BigDecimal patientHeight;

    @Column(name = "Patient_Weight", precision = 5, scale = 2)
    private BigDecimal patientWeight;

    @Lob
    @Column(name = "Patient_Blood_Group")
    private String patientBloodGroup;

    @Lob
    @Column(name = "Patient_Notes")
    private String patientNotes;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "Admission_Date")
    private Instant admissionDate;

    @Column(name = "status")
    private Byte status = 0;

}