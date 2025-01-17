package org.hidevelop.coffeecats.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.annotation.JwtAuth;
import org.hidevelop.coffeecats.client.GooglePlaceClient;
import org.hidevelop.coffeecats.model.dto.CafeTypeReviewsUpdateReqDto;
import org.hidevelop.coffeecats.model.dto.CommonResponseDto;
import org.hidevelop.coffeecats.model.dto.google_map.*;
import org.hidevelop.coffeecats.model.dto.RegisterCafeReqDto;
import org.hidevelop.coffeecats.service.CafeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe")
public class CafeController {

    private final CafeService cafeService;
    private final GooglePlaceClient googlePlaceClient;

    @JwtAuth
    @PostMapping("/register")
    public ResponseEntity<String> registerCafe(
            @Valid @RequestBody RegisterCafeReqDto registerCafeReqDto,
            HttpServletRequest request) {
        // HttpServletRequest의 getAttribute() 메서드는 Object 타입을 반환하기 때문에 원시 타입으로 바로 사용할 수 없음
        Long memberId = (Long) request.getAttribute("memberId");
        cafeService.registerCafe(registerCafeReqDto, memberId);
        return ResponseEntity.ok("카페가 등록되었습니다.");
    }

    @JwtAuth
    @GetMapping("/search-nearby/register-cafe")
    public ResponseEntity<GooglePlaceResponseDto> searchRegisterCafe(@RequestParam double longitude,
                                                                     @RequestParam double latitude) {

        GooglePlaceSearchNearByRequestDto googlePlaceSearchNearByRequestDto = new GooglePlaceSearchNearByRequestDto(
                new GooglePlaceSearchNearByRequestDto.LocationRestriction(
                        new Circle(
                                new Center(
                                        latitude,
                                        longitude
                                )
                        )
                )
        );
        GooglePlaceResponseDto googlePlaceResponseDtos = googlePlaceClient.searchNearby(googlePlaceSearchNearByRequestDto);
        return ResponseEntity.ok(googlePlaceResponseDtos);
    }

    @JwtAuth
    @GetMapping("/search-text/register-cafe")
    public ResponseEntity<?> searchRegisterCafe(@RequestParam String textQuery,
                                                @RequestParam double longitude,
                                                @RequestParam double latitude) {
        GooglePlaceSearchTextReqDto googlePlaceSearchTextReqDto = new GooglePlaceSearchTextReqDto(
                textQuery,
                new GooglePlaceSearchTextReqDto.LocationBias(
                        new Circle(
                                new Center(
                                        latitude,
                                        longitude
                                )
                        )
                )
        );
        GooglePlaceResponseDto googlePlaceResponseDto = googlePlaceClient.searchText(googlePlaceSearchTextReqDto);
        if (googlePlaceResponseDto.places() == null) {
            return ResponseEntity.ok("검색결과가 없습니다.");
        }
        return ResponseEntity.ok(googlePlaceResponseDto);
    }

    @JwtAuth
    @PutMapping("/{cafe-id}/cafe-type")
    public ResponseEntity<CommonResponseDto> updateCafeType(
            @PathVariable(name = "cafe-id") String cafeId,
            @Valid @RequestBody CafeTypeReviewsUpdateReqDto cafeTypeReviewsUpdateReqDto,
            HttpServletRequest request){

        Long memberId = (Long) request.getAttribute("memberId");
        cafeService.updateCafeType(memberId, cafeId, cafeTypeReviewsUpdateReqDto);

        return ResponseEntity.ok(new CommonResponseDto("카페 수정을 성공하였습니다."));
    }

}
