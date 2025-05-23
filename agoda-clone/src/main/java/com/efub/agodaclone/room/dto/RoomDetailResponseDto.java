package com.efub.agodaclone.room.dto;

import com.efub.agodaclone.room.domain.Room;
import com.efub.agodaclone.room.domain.RoomImage;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
public class RoomDetailResponseDto {
    private Long roomId;
    private String roomType;
    private String bed;
    private List<String> roomImages;
    private int price;
    private int discountPrice;

    public static RoomDetailResponseDto from(Room room){
        return RoomDetailResponseDto.builder()
                .roomId(room.getRoomId())
                .roomType(room.getRoomType())
                .bed(room.getBed())
                .roomImages(room.getRoomImageList().stream()
                        .map(RoomImage::getImgUrl)
                        .collect(Collectors.toList()))
                .price(room.getPrice())
                .discountPrice(room.getDiscountPrice())
                .build();
    }
}
