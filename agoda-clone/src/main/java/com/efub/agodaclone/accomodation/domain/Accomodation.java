package com.efub.agodaclone.accomodation.domain;

import com.efub.agodaclone.room.domain.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accomodation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accomodation_id")
    private Long accomodationId;

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
    private double totalScore;

    @Column(name = "cleanliness_score")
    private double cleanlinessScore;

    @Column(name = "service_score")
    private double serviceScore;

    @Column(name = "location_score")
    private double locationScore;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> roomList = new ArrayList<>();

    @OneToMany(mappedBy = "accomodation_img", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccomodationImage> accomodationImageList = new ArrayList<>();

//    @OneToMany(mappedBy = "provision_tag", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ProvisionTag> provisionTagList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Review> reviewList = new ArrayList<>();
 }
