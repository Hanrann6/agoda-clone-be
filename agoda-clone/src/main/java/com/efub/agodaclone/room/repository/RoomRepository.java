package com.efub.agodaclone.room.repository;

import com.efub.agodaclone.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    // 숙소 id로 객실 찾기
    Optional<Room> findByAccommodationId(Long accommodationId);
}
