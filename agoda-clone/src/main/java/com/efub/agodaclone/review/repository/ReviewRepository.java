package com.efub.agodaclone.review.repository;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT COUNT(rv) FROM Review rv WHERE rv.reservation.accommodation.id = :accommodationId")
    int countByAccommodationId(@Param("accommodationId") Long accommodationId);

    Optional<Review> findByReviewId(Long reviewId);

    Page<Review> findAllByReservation_Accommodation_AccommodationId(Long accommodationId, Pageable pageable);

    List<Review> findAllByReservation_Accommodation(Accommodation accommodation);

    @Query("""
    SELECT AVG(
        (r.cleanlinessScore + r.locationScore + r.serviceScore) / 3.0
    ) FROM Review r
    WHERE r.reservation.accommodation.accommodationId = :accommodationId
    """)
    Double calculateAverageScoreByAccommodation(@Param("accommodationId") Long accommodationId);

}
