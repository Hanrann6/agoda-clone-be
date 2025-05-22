package com.efub.agodaclone.accomodation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "provision_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProvisionTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provision_tag_id")
    private Long provisionTagId;

    @Enumerated(EnumType.STRING)
    private TagName tagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @Builder
    public ProvisionTag(TagName tagName, Accommodation accommodation) {
        this.tagName = tagName;
        this.accommodation = accommodation;
    }
}
