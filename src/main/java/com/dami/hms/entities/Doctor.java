package com.dami.hms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "doctor_details", schema = "hms", uniqueConstraints = {
        @UniqueConstraint(name = "Doctor_ID", columnNames = {"Doctor_ID"})
})
public class Doctor {
    @Id
    @Column(name = "Doctor_ID", nullable = false)
    private String doctorId;

    @Column(name = "Doctor_FName")
    private String doctorFname;

    @Column(name = "Doctor_LName")
    private String doctorLname;

    @Column(name = "Doctor_Sex")
    private String doctorSex;

    @Column(name = "Doctor_NID")
    private String doctorNid;

    @Column(name = "Doctor_HPhone")
    private String doctorHphone;

    @Column(name = "Doctor_MPhone")
    private String doctorMphone;

    @Column(name = "Doctor_Address")
    private String doctorAddress;

    @Column(name = "Doctor_Qualification")
    private String doctorQualification;

    @Column(name = "Doctor_Specialization")
    private String doctorSpecialization;

    @Column(name = "Doctor_Type")
    private String doctorType;

    @Column(name = "Doctor_VCharge", precision = 10, scale = 2)
    private BigDecimal doctorVcharge;

    @Column(name = "Doctor_CCharge", precision = 10, scale = 2)
    private BigDecimal doctorCcharge;

    @Lob
    @Column(name = "Doctor_Notes")
    private String doctorNotes;

    @Column(name = "Doctor_Basic_Sal", precision = 12, scale = 2)
    private BigDecimal doctorBasicSal;

    @Column(name = "status")
    private Integer status = 0;

    // Doctor.java

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DoctorScheduleDetail> schedules = new ArrayList<>();


}