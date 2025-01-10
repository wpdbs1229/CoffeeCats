package org.hidevelop.coffeecats.model.dto.google_map;


import lombok.*;

import java.util.List;

@Getter
public class GooglePlaceSearchNearByRequestDto {

    private final List<String> includedTypes;
    private final int maxResultCount;
    private final String languageCode;
    private final LocationRestriction locationRestriction;

    public GooglePlaceSearchNearByRequestDto(LocationRestriction locationRestriction) {
        this.includedTypes = List.of("cafe", "coffee_shop");
        this.maxResultCount = 20;
        this.languageCode = "ko";
        this.locationRestriction = locationRestriction;
    }

    @Getter
    public static class LocationRestriction {
        private final Circle circle;

        public LocationRestriction(Circle circle) {
            this.circle = circle;
        }
    }




}
