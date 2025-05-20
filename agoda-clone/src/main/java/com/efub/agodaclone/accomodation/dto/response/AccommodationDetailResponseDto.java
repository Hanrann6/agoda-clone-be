package com.efub.agodaclone.accomodation.dto.response;


import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.accomodation.domain.AccommodationImage;
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
    private double totalScore;
    private int reviewCount;
    private String description;
    private List<String> accommodationImgList;
    private String roomType;
    private String bed;
    private List<String> roomImage;
    private int price;
//    private List<String> provisionTagList;
    private double cleanlinessScore;
    private double serviceScore;
    private double locationScore;

    public static AccommodationDetailResponseDto from(Accommodation accommodation, Room room, int discountPrice) {
        return AccommodationDetailResponseDto.builder()
                .korName(accommodation.getKorName())
                .engName(accommodation.getEngName())
                .totalScore(accommodation.getTotalScore())
//                .reviewCount(accommodation.getReviewList.size())
                .description(accommodation.getDescription())
                .accommodationImgList(accommodation.getAccommodationImageList().stream()
                                .map(AccommodationImage::getImgUrl)
                                .collect(Collectors.toList())
                )
                .roomType(room.getRoomType())
                .bed(room.getBed())
                .roomImage(room.getRoomImageList().stream()
                        .map(RoomImage::getImgUrl)
                        .collect(Collectors.toList()))
                .price(discountPrice)
//                .provisionTag(accommodation.getProvisionTags().stream()
//                        .map(ProvisionTag::getName)
//                        .collect(Collectors.toList())
//                )
                .cleanlinessScore(accommodation.getCleanlinessScore())
                .serviceScore(accommodation.getServiceScore())
                .locationScore(accommodation.getLocationScore())
                .build();
    }

}
