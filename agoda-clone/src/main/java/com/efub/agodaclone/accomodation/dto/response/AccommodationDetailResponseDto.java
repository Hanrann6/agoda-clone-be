package com.efub.agodaclone.accomodation.dto.response;


import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.accomodation.domain.AccommodationImage;
import com.efub.agodaclone.accomodation.domain.ProvisionTag;
import com.efub.agodaclone.room.domain.Room;
import com.efub.agodaclone.room.domain.RoomImage;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
public class AccommodationDetailResponseDto {

    private String korName;
    private String engName;
    private String address;
    private double totalScore;
    private int reviewCount;
    private String description;
    private List<String> accommodationImage;
    private String roomType;
    private String bed;
    private List<String> roomImage;
    private int price;
    private int discountPrice;
    private List<String> provisionTag;
    private double cleanlinessScore;
    private double serviceScore;
    private double locationScore;

    public static AccommodationDetailResponseDto from(Accommodation accommodation, Room room, int reviewCount, int discountPrice) {
        return AccommodationDetailResponseDto.builder()
                .korName(accommodation.getKorName())
                .engName(accommodation.getEngName())
                .address(accommodation.getAddress())
                .totalScore(accommodation.getTotalScore())
                .reviewCount(reviewCount)
                .description(accommodation.getDescription())
                .accommodationImage(accommodation.getAccommodationImageList().stream()
                                .map(AccommodationImage::getImgUrl)
                                .collect(Collectors.toList())
                )
                .roomType(room.getRoomType())
                .bed(room.getBed())
                .roomImage(room.getRoomImageList().stream()
                        .map(RoomImage::getImgUrl)
                        .collect(Collectors.toList()))
                .price(accommodation.getPrice())
                .discountPrice(discountPrice)
                .provisionTag(accommodation.getProvisionTagList().stream()
                        .map(tag -> tag.getTagName().getLabel())
                        .collect(Collectors.toList())
                )
                .cleanlinessScore(accommodation.getCleanlinessScore())
                .serviceScore(accommodation.getServiceScore())
                .locationScore(accommodation.getLocationScore())
                .build();
    }
}
