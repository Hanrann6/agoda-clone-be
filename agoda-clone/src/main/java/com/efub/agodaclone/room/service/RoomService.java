package com.efub.agodaclone.room.service;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.accomodation.service.AccommodationService;
import com.efub.agodaclone.global.exception.AgodaException;
import com.efub.agodaclone.global.exception.ExceptionCode;
import com.efub.agodaclone.room.domain.Room;
import com.efub.agodaclone.room.dto.RoomDetailResponseDto;
import com.efub.agodaclone.room.dto.RoomListResponseDto;
import com.efub.agodaclone.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final AccommodationService accommodationService;

    // 객실 리스트 조회
    public RoomListResponseDto getRoomList(Long accommodationId){
        Accommodation findAccommodation = accommodationService.findAccommodationById(accommodationId);
        List<Room> findRooms = roomRepository.findByAccommodation(findAccommodation);

        return RoomListResponseDto.from(findRooms);
    }

    // 객실 상세 조회
    public RoomDetailResponseDto getDetailedRoom(Long roomId){
        Room findRoom = findByRoomId(roomId);
        return RoomDetailResponseDto.from(findRoom);
    }

    public Room findByRoomId(Long roomId){
        return roomRepository.findById(roomId)
                .orElseThrow(()->new AgodaException(ExceptionCode.RESOURCE_NOT_FOUND));
    }
}
