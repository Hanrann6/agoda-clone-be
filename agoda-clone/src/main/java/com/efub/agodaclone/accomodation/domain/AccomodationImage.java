package com.efub.agodaclone.accomodation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccomodationImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accomodation_img_id")
    private Long accomodationImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accomodation_id")
    private Accomodation accomodation;

    @Column(name = "accomodation_img")
    private String imgUrl;
}
