package org.hidevelop.coffeecats.service;

import com.github.davidmoten.geo.GeoHash;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.exception.CustomException;
import org.hidevelop.coffeecats.model.dto.RegisterCafeReqDto;
import org.hidevelop.coffeecats.model.entity.*;
import org.hidevelop.coffeecats.model.type.CafeType;
import org.hidevelop.coffeecats.repository.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static org.hidevelop.coffeecats.exception.error.impl.CafeError.*;
import static org.hidevelop.coffeecats.exception.error.impl.MemberCustomError.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    private final CafeTypeRepository cafeTypeRepository;
    private final CafeTypeMapRepository cafeTypeMapRepository;
    private final MemberCafeTypeReviewsRepository memberCafeTypeReviewsRepository;
    private final TypeReviewsMapRepository typeReviewsMapRepository;
    private final MemberRepository memberRepository;

    @Transactional(rollbackOn = CustomException.class)
    public void registerCafe(RegisterCafeReqDto registerCafeReqDto, Long registerMemberId) {

        //"place/" 제거
        String cafeId = subPlaceString(registerCafeReqDto.cafeId());
        //카페 등록하기전 유효성 검사
        validateCafeRegistration(registerCafeReqDto, cafeId);

        //geoHash 생성
        String geoHash = generateGeoHash(registerCafeReqDto.latitude(), registerCafeReqDto.longitude());


        // cafeType 유효한지 확인 후 해당 카페 타입의 ID 추출
        Set<CafeTypeEntity> cafeTypeEntitySet = new HashSet<>();
        for (CafeType cafeType : registerCafeReqDto.cafeType()) {
            cafeTypeEntitySet.add(cafeTypeRepository.findByCafeType(cafeType)
                    .orElseThrow(() -> new CustomException(DOES_NOT_EXIST_CAFE_TYPE)));
        }

        CafeEntity cafeEntity = cafeRepository.save(
                registerCafeReqDto.toCafeEntity(
                        cafeId,
                        geoHash,
                        registerCafeReqDto.latitude(),
                        registerCafeReqDto.longitude(),
                        registerMemberId));

        //cafe : cafeType Mapping 저장
        for (CafeTypeEntity cafeTypeEntity : cafeTypeEntitySet) {
            cafeTypeMapRepository.save(new CafeTypeMapEntity(cafeEntity, cafeTypeEntity));
        }

        MemberEntity memberEntity = memberRepository.findById(registerMemberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        //MemberCafeTypeReviews 저장
        MemberCafeTypeReviewsEntity memberCafeTypeReviewsEntity = memberCafeTypeReviewsRepository.save(new MemberCafeTypeReviewsEntity(
                memberEntity,
                cafeEntity
        ));

        //TypeReviewsMap 저장
        for (CafeTypeEntity cafeTypeEntity : cafeTypeEntitySet) {
            typeReviewsMapRepository.save(new TypeReviewsMapEntity(
                    cafeTypeEntity,
                    memberCafeTypeReviewsEntity));
        }

    }

    private void validateCafeRegistration(RegisterCafeReqDto registerCafeReqDto, String cafeId) {
        boolean existsById = cafeRepository.existsById(cafeId);
        if (existsById) {
            throw new CustomException(ALREADY_REGISTERED);
        }

        boolean existsByLoadAddress = cafeRepository.existsByAddress(registerCafeReqDto.address());
        if (existsByLoadAddress) {
            throw new CustomException(ALREADY_REGISTERED);
        }
    }

    private String subPlaceString(String cafeId) {
        return cafeId.substring(7);
    }

    private String generateGeoHash(double latitude, double longitude) {
        return GeoHash.encodeHash(latitude, longitude, 7);
    }
}
