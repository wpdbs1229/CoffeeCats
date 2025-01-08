package org.hidevelop.coffeecats.client;

import org.hidevelop.coffeecats.config.GooglePlaceFeignConfig;
import org.hidevelop.coffeecats.model.dto.GooglePlaceRequestDto;
import org.hidevelop.coffeecats.model.dto.GooglePlaceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;



@FeignClient(name = "google-place-client",
        url = "https://places.googleapis.com/v1/",
        configuration = GooglePlaceFeignConfig.class)
public interface GooglePlaceClient {

    @PostMapping(value = "places:searchNearby",
            headers = {"Content-Type=application/json",
                    "X-Goog-FieldMask=places.displayName,places.name,places.formattedAddress,places.location"}
    )
    GooglePlaceResponseDto searchNearby(
            GooglePlaceRequestDto googlePlaceRequestDto
    );
}
