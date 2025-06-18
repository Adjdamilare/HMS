package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.Where;
import java.util.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "services", schema = "hms")
public class Services {
    @Id
    @Column(name = "Channel_Service_ID")
    private String channelServiceId;

    @Column(name = "Channel_Service")
    private String channelService;

    @Column(name = "Duration_Of_Service")
    private BigDecimal durationOfService;

    @Column(name = "Charge_For_Service")
    private BigDecimal chargeForService;

    @Column(name = "Service_Notes")
    private String serviceNotes;

    @Column(name = "status")
    private Integer status = 0;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServiceScheduleDetail> schedules = new ArrayList<>();

}