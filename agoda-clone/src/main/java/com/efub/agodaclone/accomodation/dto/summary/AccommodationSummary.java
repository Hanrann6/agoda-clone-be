package com.efub.agodaclone.accomodation.dto.summary;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.accomodation.domain.AccommodationImage;
import com.efub.agodaclone.room.domain.Room;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
public class AccommodationSummary {

    private Long accommodationId;
    private String korName;
    private String engName;
    private int star;
    private String accommodationImage;
    private String location;
    private double totalScore;
    private int reviewCount;
    private int price;
    private int discountPrice;
    private int totalPrice;
    private List<String> provisionTags;

    public static AccommodationSummary from(Accommodation accommodation, int reviewCount, int discountPrice, int days) {
        AccommodationImage accommodationImage = accommodation.getAccommodationImageList().get(0); // 대표 이미지 1개만 사용
        return AccommodationSummary.builder()
                .accommodationId(accommodation.getAccommodationId())
                .korName(accommodation.getKorName())
                .engName(accommodation.getEngName())
                .star(accommodation.getStar())
                .accommodationImage(accommodationImage.getImgUrl())
                .location(accommodation.getLocation())
                .totalScore(accommodation.getTotalScore())
                .reviewCount(reviewCount)
                .provisionTags(accommodation.getProvisionTagList().stream()
                        .map(tag -> tag.getTagName().getLabel())
                        .collect(Collectors.toList())
                )
                .price(accommodation.getPrice())
                .discountPrice(discountPrice)
                .totalPrice(discountPrice * days)
                .build();
    }
}

