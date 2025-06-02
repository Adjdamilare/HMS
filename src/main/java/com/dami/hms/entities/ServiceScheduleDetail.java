package com.dami.hms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;

@Setter
@Getter
@Entity
@Table(name = "service_schedule_details", schema = "hms")
public class ServiceScheduleDetail {
    @Id
    @Column(name = "Service_Schedule_ID")
    private String serviceScheduleId;

    @Column(name = "Service_ID")
    private String serviceId;

    @Column(name = "Service_Starts")
    private LocalTime serviceStarts;

    @Column(name = "Service_Ends")
    private LocalTime serviceEnds;

    @Column(name = "Service_Avail_Date")
    private String serviceAvailDate;

    @Column(name = "Schedule_Notes")
    private String scheduleNotes;

    @ColumnDefault("0")
    @Column(name = "Status")
    private Integer status = 0;
}




