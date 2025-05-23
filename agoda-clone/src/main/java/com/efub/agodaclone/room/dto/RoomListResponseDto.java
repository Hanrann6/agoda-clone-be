package com.efub.agodaclone.room.dto;

import com.efub.agodaclone.room.domain.Room;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
public class RoomListResponseDto {
    private List<RoomSummary> rooms;

    public static RoomListResponseDto from(List<Room> rooms) {
        List<RoomSummary> summaries = rooms.stream()
                .map(RoomSummary::from)
                .collect(Collectors.toList());

        return RoomListResponseDto.builder()
                .rooms(summaries)
                .build();
    }
}
