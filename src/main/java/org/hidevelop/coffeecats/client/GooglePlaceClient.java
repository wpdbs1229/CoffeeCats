package org.hidevelop.coffeecats.client;

import org.hidevelop.coffeecats.config.GooglePlaceFeignConfig;
import org.hidevelop.coffeecats.model.dto.google_map.GooglePlaceSearchNearByRequestDto;
import org.hidevelop.coffeecats.model.dto.google_map.GooglePlaceResponseDto;
import org.hidevelop.coffeecats.model.dto.google_map.GooglePlaceSearchTextReqDto;
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
            GooglePlaceSearchNearByRequestDto googlePlaceSearchNearByRequestDto
    );

    @PostMapping(value = "places:searchText",
        headers = {"Content-Type=application/json",
                "X-Goog-FieldMask=places.displayName,places.name,places.formattedAddress,places.location"}
    )
    GooglePlaceResponseDto searchText(
            GooglePlaceSearchTextReqDto googlePlaceSearchTextReqDto
    );
}
