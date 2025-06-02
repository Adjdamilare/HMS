package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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

    @Column(name = "room_Ward")
    private String roomWard;

    @Column(name = "bed_description")
    private String bedDescription;
}