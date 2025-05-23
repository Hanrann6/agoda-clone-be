package com.efub.agodaclone.room.repository;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    // 숙소 객실 찾기
    List<Room> findByAccommodation(Accommodation accommodation);
}
