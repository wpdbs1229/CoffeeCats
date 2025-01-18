package org.hidevelop.coffeecats.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.annotation.JwtAuth;
import org.hidevelop.coffeecats.client.GooglePlaceClient;
import org.hidevelop.coffeecats.model.dto.CafeDescriptionUpdateReqDto;
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

    /***
     * 카페 등록 API
     * @param registerCafeReqDto 카페 정보 DTO
     * @param request JWT 분해를 위해
     * @return 카페 등록 성공 값
     */
    @JwtAuth
    @PostMapping("/register")
    public ResponseEntity<CommonResponseDto> registerCafe(
            @Valid @RequestBody RegisterCafeReqDto registerCafeReqDto,
            HttpServletRequest request) {
        // HttpServletRequest의 getAttribute() 메서드는 Object 타입을 반환하기 때문에 원시 타입으로 바로 사용할 수 없음
        Long memberId = (Long) request.getAttribute("memberId");
        cafeService.registerCafe(registerCafeReqDto, memberId);
        return ResponseEntity.ok(new CommonResponseDto("카페 등록에 성공했습니다."));
    }

    /***
     * 등록할 카페를 주변에서 검색
     * @param longitude 사용자의 경도
     * @param latitude 사용자의 위도
     * @return 주변 카페 목록
     */
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

    /***
     * 등록할 카페를 주변에서 텍스트로 검색
     * @param textQuery 검색어
     * @param longitude 사용자 경도
     * @param latitude 사용자 위도
     * @return 검색한 언어와 관련된 주변 카페 목록
     */
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

    /***
     * 카페 타입 수정 API
     * @param cafeId 수정할려고 하는 카페 ID
     * @param cafeTypeReviewsUpdateReqDto 수정할려는 cafe Type List
     * @param request JWT
     * @return 카페 수정 성공 메시지
     */
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

    /***
     * 카페 설명 수정 API
     * @param cafeId 수정할려고 하는 카페 ID
     * @param cafeDescriptionUpdateReqDto 수정할 카페 설명
     * @param request JWT
     * @return 카페 수정 메시지
     */
    @JwtAuth
    @PutMapping("/{cafe-id}/cafe-description")
    public ResponseEntity<CommonResponseDto> updateCafeDescription(
            @PathVariable(name = "cafe-id") String cafeId,
            @Valid @RequestBody CafeDescriptionUpdateReqDto cafeDescriptionUpdateReqDto,
            HttpServletRequest request
    ){

        Long memberId = (Long) request.getAttribute("memberId");
        cafeService.updateCafeDescription(memberId, cafeId, cafeDescriptionUpdateReqDto);
        return ResponseEntity.ok(new CommonResponseDto("카페 수정을 성공하였습니다."));
    }
}
