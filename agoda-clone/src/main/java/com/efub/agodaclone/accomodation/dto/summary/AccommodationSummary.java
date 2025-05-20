package com.efub.agodaclone.accomodation.dto.summary;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.room.domain.Room;
import com.efub.agodaclone.room.domain.RoomImage;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
public class AccommodationSummary {

    private String korName;
    private String engName;
    private double totalScore;
    private int reviewCount;
    private String description;
    //    private List<String> provisionTagList;

    private String roomType;
    private String bed;
    private List<String> roomImages;

    private int discountPrice;
    private int totalPrice;

    private double cleanlinessScore;
    private double serviceScore;
    private double locationScore;

    public static AccommodationSummary from(Accommodation accommodation, int discountPrice, int days) {
        Room room = accommodation.getRoomList().get(0); // 객실 1개만 사용
        return AccommodationSummary.builder()
                .korName(accommodation.getKorName())
                .engName(accommodation.getEngName())
                .totalScore(accommodation.getTotalScore())
//                .reviewCount(accomodation.getReviewList().size())
                .description(accommodation.getDescription())
//                .provisionTag(accomodation.getProvisionTags().stream()
//                        .map(ProvisionTag::getName)
//                        .collect(Collectors.toList())
//                )
                .roomType(room.getRoomType())
                .bed(room.getBed())
                .roomImages(room.getRoomImageList().stream()
                        .map(RoomImage::getImgUrl)
                        .collect(Collectors.toList()))
                .discountPrice(discountPrice)
                .totalPrice(discountPrice * days)
                .cleanlinessScore(accommodation.getCleanlinessScore())
                .serviceScore(accommodation.getServiceScore())
                .locationScore(accommodation.getLocationScore())
                .build();
    }
}

