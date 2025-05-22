package com.efub.agodaclone.accomodation.repository;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.room.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    // 숙소 검색 키워드
    Page<Accommodation> findByLocationContainingAndPriceBetween(String location, int minPrice, int maxPrice, Pageable pageable);

    // 숙소 id로 조회
    Optional<Accommodation> findByAccommodationId(Long accommodationId);
}
