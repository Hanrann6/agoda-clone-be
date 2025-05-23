package com.efub.agodaclone.room.dto;

import com.efub.agodaclone.room.domain.Room;
import com.efub.agodaclone.room.domain.RoomImage;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @Builder
public class RoomSummary {
    private Long roomId;
    private String roomType;
    private String bed;
    private String roomImage;
    private int price;
    private int discountPrice;

    public static RoomSummary from(Room room){
        List<RoomImage> imageList = room.getRoomImageList();
        String mainImage = imageList.isEmpty() ? null : imageList.get(0).getImgUrl(); // 대표 이미지 1개

        return RoomSummary.builder()
                .roomId(room.getRoomId())
                .roomType(room.getRoomType())
                .bed(room.getBed())
                .roomImage(mainImage)
                .price(room.getPrice())
                .discountPrice(room.getDiscountPrice())
                .build();
    }
}
