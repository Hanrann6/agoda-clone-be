package com.efub.agodaclone.room.service;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.global.exception.AgodaException;
import com.efub.agodaclone.global.exception.ExceptionCode;
import com.efub.agodaclone.room.domain.Room;
import com.efub.agodaclone.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room findByRoomId(Long roomId){
        return roomRepository.findById(roomId)
                .orElseThrow(()->new AgodaException(ExceptionCode.RESOURCE_NOT_FOUND));
    }

    // 숙소로 객실 조회하는 함수
    public Room findRoomByAccommodation(Accommodation accommodation){
        return roomRepository.findByAccommodation(accommodation)
                .orElseThrow(()->new AgodaException(ExceptionCode.RESOURCE_NOT_FOUND));
    }
}
