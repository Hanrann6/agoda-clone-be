package com.efub.agodaclone.review.service;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.accomodation.repository.AccommodationRepository;
import com.efub.agodaclone.review.domain.Review;
import com.efub.agodaclone.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationScoreUpdater {
    private final ReviewRepository reviewRepository;
    private final AccommodationRepository accommodationRepository;

    @Async
    public void updateAccommodationScore(Accommodation accommodation){
        List<Review> reviews = reviewRepository.findAllByReservation_Accommodation(accommodation);

        if(reviews.isEmpty()){
            return;
        }

        double avgClean = reviews.stream().mapToInt(Review::getCleanlinessScore).average().orElse(0.0);
        double avgService = reviews.stream().mapToInt(Review::getServiceScore).average().orElse(0.0);
        double avgLocation = reviews.stream().mapToInt(Review::getLocationScore).average().orElse(0.0);
        double totalAvg = (avgClean + avgService + avgLocation) / 3.0;
        log.info("Total cleanliness score: {}",totalAvg);
        accommodation.updateScores(avgClean, avgService, avgLocation, totalAvg);
        accommodationRepository.save(accommodation);
    }
}
