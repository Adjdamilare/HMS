package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ward_details", schema = "hms")
public class WardDetail {
    @Id
    @Column(name = "ward_id")
    private String wardId;

    @Column(name = "ward_name")
    private String wardName;

    @Column(name = "ward_rate")
    private BigDecimal wardRate;

    @Column(name = "ward_desc")
    private String wardDesc;


    @Column(name = "status")
    private Byte status = 0;

}