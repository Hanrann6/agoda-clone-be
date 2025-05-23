package com.efub.agodaclone.accomodation.service;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.global.exception.AgodaException;
import com.efub.agodaclone.global.exception.ExceptionCode;
import com.efub.agodaclone.review.repository.ReviewRepository;
import com.efub.agodaclone.room.domain.Room;
import com.efub.agodaclone.accomodation.dto.response.AccommodationDetailResponseDto;
import com.efub.agodaclone.accomodation.dto.response.AccommodationSearchListResponseDto;
import com.efub.agodaclone.accomodation.repository.AccommodationRepository;
import com.efub.agodaclone.room.repository.RoomRepository;
import com.efub.agodaclone.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final ReviewRepository reviewRepository;

    // 숙소 검색 리스트
    public AccommodationSearchListResponseDto getAccommodationList(String query, LocalDate startDate, LocalDate endDate, int minPrice, int maxPrice, int page){
        if (page < 0) {
            throw new AgodaException(ExceptionCode.INVALID_PAGE);
        }
        if (startDate.isAfter(endDate)) {
            throw new AgodaException(ExceptionCode.INVALID_DATE_RANGE);
        }

        Pageable pageable = PageRequest.of(page, 8);
        Page<Accommodation> accommodationList = accommodationRepository.findByLocationContainingAndPriceBetween(query, minPrice, maxPrice, pageable);

        int totalPages = accommodationList.getTotalPages();
        int totalCount = (int) accommodationList.getTotalElements();
        int days = getDays(startDate, endDate);

        return AccommodationSearchListResponseDto.from(accommodationList, page, totalPages, totalCount, days, this::calculateDiscountPrice, this::getReviewCount);
    }

    // 숙소 상세 정보 조회
    public AccommodationDetailResponseDto getDetailedAccommodation(Long accommodationId){
        Accommodation accommodation = findAccommodationById(accommodationId);
        int reviewCount = getReviewCount(accommodationId);
        int discountPrice = calculateDiscountPrice(accommodation.getPrice(), accommodation.getDiscountRate());

        return AccommodationDetailResponseDto.from(accommodation, reviewCount, discountPrice);
    }

    // 숙소 ID로 숙소 조회하는 함수
    public Accommodation findAccommodationById(Long accommodationId){
        return accommodationRepository.findByAccommodationId(accommodationId)
                .orElseThrow(()->new AgodaException(ExceptionCode.RESOURCE_NOT_FOUND));
    }

    // 숙박하는 일 수 계산하는 함수
    public int getDays(LocalDate startDate, LocalDate endDate){
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    // 할인 후 금액 계산하는 함수
    private int calculateDiscountPrice(int price, int discountRate){
        return (int) (price * (1 - discountRate / 100.0));
    }

    // 숙소 리뷰 개수 반환하는 함수
    private int getReviewCount(Long accommodationId){
        return reviewRepository.countByAccommodationId(accommodationId);
    }
}
