package com.efub.agodaclone.accomodation.service;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.accomodation.dto.request.AccommodationSearchRequestDto;
import com.efub.agodaclone.room.domain.Room;
import com.efub.agodaclone.accomodation.dto.response.AccommodationDetailResponseDto;
import com.efub.agodaclone.accomodation.dto.response.AccommodationSearchListResponseDto;
import com.efub.agodaclone.accomodation.repository.AccommodationRepository;
import com.efub.agodaclone.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;

    // 숙소 검색 리스트
    public AccommodationSearchListResponseDto getAccommodationList(AccommodationSearchRequestDto accommodationSearchRequestDto){
        List<Accommodation> accommodationList = accommodationRepository.findByLocationContaining(accommodationSearchRequestDto.getQuery());
        int days = getDays(accommodationSearchRequestDto.getStartDate(), accommodationSearchRequestDto.getEndDate());

        return AccommodationSearchListResponseDto.from(accommodationList, days, this::calculateDiscountPrice);
    }

    // 숙소 상세 정보 조회
    public AccommodationDetailResponseDto getDetailedAccommodation(Long accomodationId){
        Accommodation accommodation = accommodationRepository.findByAccommodationId(accomodationId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 숙소입니다."));
        Room room = findByAccommodationId(accomodationId);
        int discountPrice = calculateDiscountPrice(accommodation.getPrice(), accommodation.getDiscountRate());

        return AccommodationDetailResponseDto.from(accommodation, room, discountPrice);
    }

    // 숙소 id로 객실 조회하는 함수
    public Room findByAccommodationId(Long accommodationId){
        return roomRepository.findByAccommodationId(accommodationId)
                .orElseThrow(()-> new IllegalArgumentException("방이 존재하지 않습니다."));
    }

    // 숙박하는 일 수 계산하는 함수
    public int getDays(LocalDate startDate, LocalDate endDate){
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    //
    private int calculateDiscountPrice(int price, int discountRate) {
        return (int) (price * (1 - discountRate / 100.0));
    }
}
