package com.efub.agodaclone.room.domain;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.reservation.domain.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservationList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImage> roomImageList = new ArrayList<>();

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(nullable = false)
    private String bed;

    @Column(nullable = false)
    private int price;

    @Column(name = "discount_price", nullable = false)
    private int discountPrice;
}
