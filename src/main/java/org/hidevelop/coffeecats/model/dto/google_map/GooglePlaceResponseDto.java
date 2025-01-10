package org.hidevelop.coffeecats.model.dto.google_map;

import java.util.List;

public record GooglePlaceResponseDto(
        List<Place> places
){}

