package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "room_details", schema = "hms")
public class RoomDetail {
    @Id
    @Column(name = "room_id")
    private String roomId;

    @NotNull
    @Column(name = "room_type")
    private String roomType;

    @Column(name = "room_description")
    private String roomDescription;

    @Column(name = "status")
    private Byte status = 0;

}