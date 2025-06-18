package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "outpatient_details", schema = "hms")
public class Outpatient {
    @Id
    @Size(max = 255)
    @Column(name = "Patient_ID", nullable = false)
    private String patientId;

    @Lob
    @Column(name = "First_Name")
    private String firstName;

    @Lob
    @Column(name = "Last_Name")
    private String lastName;

    @Lob
    @Column(name = "Gender")
    private String gender;

    @Lob
    @Column(name = "Address")
    private String address;

    @Lob
    @Column(name = "Telephone")
    private String telephone;

    @Lob
    @Column(name = "Status")
    private String status;

    @Lob
    @Column(name = "Notes")
    private String notes;

    @Column(name = "active")
    private Byte active  = 0;

}