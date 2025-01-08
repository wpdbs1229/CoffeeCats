package org.hidevelop.coffeecats.model.dto;


import lombok.*;

import java.util.List;

@Getter
public class GooglePlaceRequestDto {

    private final List<String> includedTypes;
    private final int maxResultCount;
    private final String languageCode;
    private final LocationRestriction locationRestriction;

    public GooglePlaceRequestDto(LocationRestriction locationRestriction) {
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

    @Getter
    public static class Circle {
        private final Center center;
        private final double radius;

        public Circle(Center center) {
            this.center = center;
            this.radius = 500.0;
        }
    }

    @Getter
    public static class Center {
        private final Double latitude;
        private final Double longitude;

        public Center(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
