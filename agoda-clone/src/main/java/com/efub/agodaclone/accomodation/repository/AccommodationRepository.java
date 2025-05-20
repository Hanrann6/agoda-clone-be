package com.efub.agodaclone.accomodation.repository;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    // 숙소 검색 키워드
    List<Accommodation> findByLocationContaining(String query);

    // 숙소 id로 조회
    Optional<Accommodation> findByAccommodationId(Long accommodationId);
}
