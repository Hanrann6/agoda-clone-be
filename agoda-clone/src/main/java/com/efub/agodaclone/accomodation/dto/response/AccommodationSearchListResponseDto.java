package com.efub.agodaclone.accomodation.dto.response;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.accomodation.dto.summary.AccommodationSummary;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Builder @Getter
public class AccommodationSearchListResponseDto {

    private List<AccommodationSummary> accommodations;
    private int currentPage;
    private int totalPage;
    private int totalCount;

    public static AccommodationSearchListResponseDto from(Page<Accommodation> accommodationList,
                                                          int currentPage,
                                                          int totalPage,
                                                          int totalCount,
                                                          int days,
                                                          BiFunction<Integer, Integer, Integer> discountCalculator) {
        List<AccommodationSummary> summaries = accommodationList.stream()
                .map(accommodation -> {
                    int discountPrice = discountCalculator.apply(accommodation.getPrice(), accommodation.getDiscountRate());
                    return AccommodationSummary.from(accommodation, discountPrice, days);
                })
                .collect(Collectors.toList());

        return AccommodationSearchListResponseDto.builder()
                .accommodations(summaries)
                .currentPage(currentPage)
                .totalPage(totalPage)
                .totalCount(totalCount)
                .build();
    }
}
