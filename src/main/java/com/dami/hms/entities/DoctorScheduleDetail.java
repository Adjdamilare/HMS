package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.Where;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "doctor_schedule_details", schema = "hms")
public class DoctorScheduleDetail {
    @Id
    @Column(name = "Doctor_Schedule_ID")
    private String doctorScheduleId;


    @Column(name = "Doctor_In")
    private LocalTime doctorIn;

    @Column(name = "Doctor_Out")
    private LocalTime doctorOut;

    @Column(name = "Doctor_Avail_Date")
    private String doctorAvailDate;

    @Column(name = "Schedule_Notes")
    private String scheduleNotes;

    @ColumnDefault("0")
    @Column(name = "Status")
    private Integer status = 0;

    // This is used to receive input from the form
    @Transient // Prevent JPA from saving this directly
    private String doctorId;

//    public String getDoctorId() {
//        return doctor != null ? doctor.getDoctorId() : null;
//    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @ToString.Exclude
    // This is the actual relationship used by JPA/Hibernate
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Doctor_ID", referencedColumnName = "Doctor_ID", nullable = false)
    private Doctor doctor;

}