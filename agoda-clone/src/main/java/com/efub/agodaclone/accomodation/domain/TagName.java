package com.efub.agodaclone.accomodation.domain;

public enum TagName {
    BREAKFAST("조식"),
    FITNESS_CENTER("피트니스 센터"),
    FREE_WIFI("무료 Wi-Fi"),
    WELCOME_DRINK("웰컴 드링크"),
    EXPRESS_CHECKIN("익스프레스 체크인"),
    GOLDEN_WEEK("GOLDEN WEEK"),
    NEW_BUILDING_2025("2025 새로 지어진 숙소");

    private final String label;

    TagName(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
