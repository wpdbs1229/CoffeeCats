package org.hidevelop.coffeecats.model.dto.google_map;

import lombok.Getter;


@Getter
public class GooglePlaceSearchTextReqDto {

    private final String textQuery;
    private final String includedType;
    private final int pageSize;
    private final String languageCode;
    private final LocationBias locationBias;

    public GooglePlaceSearchTextReqDto(String textQuery, LocationBias locationBias) {
        this.includedType = "cafe";
        this.languageCode = "ko";
        this.pageSize = 20;
        this.textQuery = textQuery;
        this.locationBias = locationBias;
    }

    @Getter
    public static class LocationBias {
        private final Circle circle;

        public LocationBias(Circle circle) {
            this.circle = circle;
        }
    }
}
