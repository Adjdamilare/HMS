package com.dami.hms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bed_details", schema = "hms")
public class BedDetail {
    @Id
    @Column(name = "bed_id")
    private String bedId;

    @Column(name = "room_ward")
    private String roomWard;

    @Column(name = "bed_description")
    private String bedDescription;

    @Column(name = "status")
    private Byte status = 0;
}