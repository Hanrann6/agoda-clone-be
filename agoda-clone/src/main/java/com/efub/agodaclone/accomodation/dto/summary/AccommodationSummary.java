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

    private String korName;
    private String engName;
    private int star;
    private List<String> accommodationImgList;
    private String location;
    private double totalScore;
    private int reviewCount;
    private int price;
    private int discountPrice;
    private int totalPrice;
    private List<String> provisionTagList;

    public static AccommodationSummary from(Accommodation accommodation, int reviewCount, int discountPrice, int days) {
        Room room = accommodation.getRoomList().get(0); // 객실 1개만 사용
        return AccommodationSummary.builder()
                .korName(accommodation.getKorName())
                .engName(accommodation.getEngName())
                .star(accommodation.getStar())
                .accommodationImgList(accommodation.getAccommodationImageList().stream()
                        .map(AccommodationImage::getImgUrl)
                        .collect(Collectors.toList())
                )
                .location(accommodation.getLocation())
                .totalScore(accommodation.getTotalScore())
                .reviewCount(reviewCount)
                .provisionTagList(accommodation.getProvisionTagList().stream()
                        .map(tag -> tag.getTagName().getLabel())
                        .collect(Collectors.toList())
                )
                .price(accommodation.getPrice())
                .discountPrice(discountPrice)
                .totalPrice(discountPrice * days)
                .build();
    }
}

