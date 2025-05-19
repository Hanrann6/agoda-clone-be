package com.efub.agodaclone.accomodation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_img_id")
    private Long roomImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Long roomId;

    @Column(name = "room_img")
    private String roomImg;
}
