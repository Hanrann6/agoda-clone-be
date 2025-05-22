package com.efub.agodaclone.review.repository;

import com.efub.agodaclone.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT COUNT(rv) FROM Review rv WHERE rv.reservation.accommodation.id = :accommodationId")
    int countByAccommodationId(@Param("accommodationId") Long accommodationId);
}
