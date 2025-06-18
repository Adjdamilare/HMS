package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "room_details", schema = "hms")
public class RoomDetail {
    @Id
    @Column(name = "room_id")
    private String roomId;

//    @NotNull
//    @Column(name = "room_type")
//    private String roomType;

    @Column(name = "room_description")
    private String roomDescription;

    @Column(name = "status")
    private Byte status = 0;


//    // This is used to receive input from the form
//    @Transient // Prevent JPA from saving this directly
//    private String roomType;
//

    @NotNull
    @Column(name = "room_type") // This stores the foreign key
    private String roomType; // Optional if using entity relationship only for JPA

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @ManyToOne
    @JoinColumn(name = "room_type", referencedColumnName = "room_type", insertable = false, updatable = false)
    private RoomType roomTypeEntity; // This maps to the RoomType entity

}