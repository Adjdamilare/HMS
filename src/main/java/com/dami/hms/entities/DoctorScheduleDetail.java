package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "doctor_schedule_details", schema = "hms")
public class DoctorScheduleDetail {
    @Id
    @Column(name = "Doctor_Schedule_ID")
    private String doctorScheduleId;

    @NotNull
    @Column(name = "Doctor_ID")
    private String doctorId;

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
}