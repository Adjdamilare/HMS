package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "guardian_details", schema = "hms")
public class GuardianDetail {
    @Id
    @Size(max = 255)
    @Column(name = "guardian_id", nullable = false)
    private String guardianId;

    @Lob
    @Column(name = "guardian_fname")
    private String guardianFname;

    @Lob
    @Column(name = "guardian_lname")
    private String guardianLname;

    @Lob
    @Column(name = "guardian_nic")
    private String guardianNic;

    @Lob
    @Column(name = "guardian_address")
    private String guardianAddress;

    @Column(name = "guardian_phone")
    private Long guardianPhone;

    @Lob
    @Column(name = "guardian_occupation")
    private String guardianOccupation;

}