package com.efub.agodaclone.accomodation.domain;

import com.efub.agodaclone.reservation.domain.Reservation;
import com.efub.agodaclone.room.domain.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accommodation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long accommodationId;

    @Column(name = "kor_name")
    private String korName;

    @Column(name = "eng_name")
    private String engName;

    private int star;
    private String address;
    private String location;
    private int price;

    @Lob
    private String description;

    @Column(name = "discount_rate")
    private int discountRate;

    @Column(name = "total_score")
    private Double totalScore = 0.0;

    @Column(name = "cleanliness_score")
    private double cleanlinessScore = 0.0;

    @Column(name = "service_score")
    private double serviceScore = 0.0;

    @Column(name = "location_score")
    private double locationScore = 0.0;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> roomList = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccommodationImage> accommodationImageList = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProvisionTag> provisionTagList = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservationList = new ArrayList<>();
 }
