package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "room_types", schema = "hms")
public class RoomType {
    @Id
    @Column(name = "room_type")
    private String roomType;

    @Column(name = "room_rates")
    private BigDecimal roomRates;

    @Column(name = "notes")
    private String notes;

    @Column(name = "status")
    private Byte status = 0;

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomDetail> roomDetails = new ArrayList<>();


}