package com.efub.agodaclone.accomodation.dto.response;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.accomodation.dto.summary.AccommodationSummary;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Builder @Getter
public class AccommodationSearchListResponseDto {

    private List<AccommodationSummary> accommodations;
    private int currentPage;
    private int totalPage;
    private int totalCount;

    public static AccommodationSearchListResponseDto from(List<Accommodation> accommodationList, int days, BiFunction<Integer, Integer, Integer> discountCalculator) {
        List<AccommodationSummary> summaries = accommodationList.stream()
                .map(accomodation -> {
                    int discountPrice = discountCalculator.apply(accomodation.getPrice(), accomodation.getDiscountRate());
                    return AccommodationSummary.from(accomodation, discountPrice, days);
                })
                .collect(Collectors.toList());

        return AccommodationSearchListResponseDto.builder()
                .accommodations(summaries)
                .currentPage(1)
                .totalPage(1)
                .totalCount(summaries.size())
                .build();
    }
}
