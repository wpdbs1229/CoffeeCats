package org.hidevelop.coffeecats.model.dto.google_map;

import lombok.Getter;

@Getter
public class Center {
    private final Double latitude;
    private final Double longitude;

    public Center(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}