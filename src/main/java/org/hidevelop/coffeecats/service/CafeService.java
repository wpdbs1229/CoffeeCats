package org.hidevelop.coffeecats.service;

import com.github.davidmoten.geo.GeoHash;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.exception.CustomException;
import org.hidevelop.coffeecats.model.dto.CafeDescriptionUpdateReqDto;
import org.hidevelop.coffeecats.model.dto.CafeTypeReviewsUpdateReqDto;
import org.hidevelop.coffeecats.model.dto.RegisterCafeReqDto;
import org.hidevelop.coffeecats.model.entity.*;
import org.hidevelop.coffeecats.model.type.CafeType;
import org.hidevelop.coffeecats.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.hidevelop.coffeecats.exception.error.impl.CafeError.*;
import static org.hidevelop.coffeecats.exception.error.impl.MemberCustomError.MEMBER_NOT_FOUND;
import static org.hidevelop.coffeecats.exception.error.impl.ReviewError.*;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    private final CafeTypeRepository cafeTypeRepository;
    private final CafeTypeMapRepository cafeTypeMapRepository;
    private final MemberCafeTypeReviewsRepository memberCafeTypeReviewsRepository;
    private final TypeReviewsMapRepository typeReviewsMapRepository;
    private final MemberRepository memberRepository;

    /***
     * 카페 등록
     * @param registerCafeReqDto 등록할려는 cafe info를 가지고 있는 DTO
     * @param registerMemberId 카페를 등록할려는 유저
     */
    @Transactional(rollbackOn = CustomException.class)
    public void registerCafe(RegisterCafeReqDto registerCafeReqDto, Long registerMemberId) {

        //1. "place/" 제거
        String cafeId = subPlaceString(registerCafeReqDto.cafeId());

        //2. 카페 등록하기전 유효성 검사
        validateCafeRegistration(registerCafeReqDto, cafeId);

        //3. geoHash 생성
        String geoHash = generateGeoHash(registerCafeReqDto.latitude(), registerCafeReqDto.longitude());


        //4. cafeType 유효한지 확인 후 해당 카페 타입의 ID 추출
        Set<CafeTypeEntity> cafeTypeEntitySet = new HashSet<>();
        for (CafeType cafeType : registerCafeReqDto.cafeType()) {
            cafeTypeEntitySet.add(cafeTypeRepository.findByCafeType(cafeType)
                    .orElseThrow(() -> new CustomException(DOES_NOT_EXIST_CAFE_TYPE)));
        }
        //5. 카페를 DB에 저장
        CafeEntity cafeEntity = cafeRepository.save(
                registerCafeReqDto.toCafeEntity(
                        cafeId,
                        geoHash,
                        registerCafeReqDto.latitude(),
                        registerCafeReqDto.longitude(),
                        registerMemberId));

        //6. cafe : cafeType Mapping 저장 (카페가 어떤 카페 타입을 가지고 있는 지를 DB에 Mapping 형태로 저장)
        for (CafeTypeEntity cafeTypeEntity : cafeTypeEntitySet) {
            cafeTypeMapRepository.save(new CafeTypeMapEntity(cafeEntity, cafeTypeEntity));
        }

        MemberEntity memberEntity = memberRepository.findById(registerMemberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        //MemberCafeTypeReviews 저장 (멤버가 자신이 어떤 카페에 리뷰를 했는지 저장)
        MemberCafeTypeReviewsEntity memberCafeTypeReviewsEntity = memberCafeTypeReviewsRepository.save(new MemberCafeTypeReviewsEntity(
                memberEntity,
                cafeEntity
        ));

        //TypeReviewsMap 저장 (멤버가 자신이 어떤 카페에 어떤 리뷰들을 했는지 저장)
        for (CafeTypeEntity cafeTypeEntity : cafeTypeEntitySet) {
            typeReviewsMapRepository.save(new TypeReviewsMapEntity(
                    cafeTypeEntity,
                    memberCafeTypeReviewsEntity));
        }

    }

    /***
     * 카페를 등록하기 전 카페가 유효한 카페인지 검증하는 함수
     * 1. 이미 등록된 카페인가?
     * 2. 주소가 중복되지는 않은가?
     * @param registerCafeReqDto 등록하려는 카페 info를 담은 DTO
     * @param cafeId "/places"를 제거한 cafeId
     */
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

    /**
     * 구글 MAP의 CafeID에서 "plaecs/" 제거하는 함수
     *
     * @param cafeId 구글에서 받아온 cafeId
     * @return "places/" 제거한 cafeId
     */
    private String subPlaceString(String cafeId) {
        return cafeId.substring(7);
    }

    /***
     * 위도, 경도로 GeoHash 값 생성
     * @param latitude 위도
     * @param longitude 경도
     * @return geoHash 값
     */
    private String generateGeoHash(double latitude, double longitude) {
        return GeoHash.encodeHash(latitude, longitude, 7);
    }

    /***
     * 카페 태그 수정
     * @param memberId 리뷰를 수정할려는 유저 PK
     * @param cafeId 리뷰를 수정할려는 Cafe PK
     * @param cafeTypeReviewsUpdateReqDto 수정할려는 새로운 카페타입 List
     * @return
     */
    @Transactional
    public void updateCafeType(Long memberId, String cafeId, CafeTypeReviewsUpdateReqDto cafeTypeReviewsUpdateReqDto) {

        //1번 멤버 리뷰 테이블에서 카페 타입 수정 정보 반영
        //1-1. 수정할려는 카페 타입이 DB에 있는 지 확인 및 추출
        Set<CafeTypeEntity> updateCafeTypeSet = new HashSet<>();
        for (CafeType cafeType : cafeTypeReviewsUpdateReqDto.cafeType()) {
            updateCafeTypeSet.add(cafeTypeRepository.findByCafeType(cafeType)
                    .orElseThrow(() -> new CustomException(DOES_NOT_EXIST_CAFE_TYPE)));
        }

        //1-2. 수정할려는 카페 리뷰 객체 탐색
        MemberCafeTypeReviewsEntity memberCafeTypeReviewsEntity = memberCafeTypeReviewsRepository.findIdByMemberIdAndCafeId(memberId, cafeId)
                .orElseThrow(() -> new CustomException(DOES_NOT_EXIST_REVIEW));

        //1-3. 기존에 카페에 등록된 카페타입 리뷰 추출
        List<TypeReviewsMapEntity> existingTypeReviewsMapEntities
                = typeReviewsMapRepository.findByMemberCafeTypeReviewId(memberCafeTypeReviewsEntity.getMemberCafeTypeReviewId());

        //1-4. 기존의 등록된 카페 타입 목록에서 새로 등록할려는 카페 타입목록에 포함되지 않는 카페 타입을 탐색하여 제거
        List<TypeReviewsMapEntity> deleteTypeReviewsMapEntities = existingTypeReviewsMapEntities.stream()
                .filter(existingTypeReviewsMapEntity -> !updateCafeTypeSet.contains(existingTypeReviewsMapEntity.getCafeTypeEntity()))
                .toList();
        typeReviewsMapRepository.deleteAll(deleteTypeReviewsMapEntities);

        //1-5. 새롭게 등록할 카페 타입에서 기존의 중복으로 등록되어있는 카페 타입은 제거하고 저장
        List<TypeReviewsMapEntity> newTypeReviewsMapEntities = updateCafeTypeSet.stream()
                .filter(cafeTypeEntity -> !existingTypeReviewsMapEntities.stream()
                        .map(TypeReviewsMapEntity::getCafeTypeEntity)
                        .toList()
                        .contains(cafeTypeEntity))
                .map(cafeTypeEntity -> new TypeReviewsMapEntity(
                        cafeTypeEntity,
                        memberCafeTypeReviewsEntity))
                .toList();
        typeReviewsMapRepository.saveAll(newTypeReviewsMapEntities);

        //2. 카페가 가지고 있는 카페 타입(CafeTypeMap)에서 수정된 사항을 반영
        //2-1.제거할 deleteTypeReview 에서 CafeTypEntity 추출
        List<CafeTypeEntity> deleteCafeTypeEntities = deleteTypeReviewsMapEntities.stream()
                .map(TypeReviewsMapEntity::getCafeTypeEntity)
                .toList();

        //2-2.추출한 CafeTypeEntity 을 통해 CafeTypeMapEntity 를 추출
        List<CafeTypeMapEntity> decreaseCafeTypeMapEntities = deleteCafeTypeEntities.stream()
                .map(deleteCafeTypeEntity -> cafeTypeMapRepository.findByCafeTypeEntity(deleteCafeTypeEntity)
                        .orElseThrow(() -> new CustomException(DOES_NOT_EXIST_REVIEW)))
                .toList();

        //2-3.CafeTypeMapEntity 에서 typeReviewCount 1 감소
        // 이때, typeReviewCount == 0 이면 삭제, 아니면 수정
        List<CafeTypeMapEntity> deleteCafeTypeMapEntities = new ArrayList<>();
        List<CafeTypeMapEntity> updateCafeTypeMapEntities = new ArrayList<>();

        for (CafeTypeMapEntity cafeTypeMapEntity : decreaseCafeTypeMapEntities) {
            cafeTypeMapEntity.decreaseTypeReviewCount();

            if (cafeTypeMapEntity.getTypeReviewCount() == 0) {
                deleteCafeTypeMapEntities.add(cafeTypeMapEntity);
            } else {
                updateCafeTypeMapEntities.add(cafeTypeMapEntity);
            }
        }
        cafeTypeMapRepository.deleteAll(deleteCafeTypeMapEntities);


        //2-4. 새로운 리뷰된 카페 타입 값들을 저장
        List<CafeTypeEntity> newCafeTypeEntities = newTypeReviewsMapEntities.stream()
                .map(TypeReviewsMapEntity::getCafeTypeEntity)
                .toList();

        CafeEntity cafeEntity = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CustomException(DOES_NOT_EXIST_CAFE));

        newCafeTypeEntities.stream()
                .map(newCafeTypeEntity -> {
                    Optional<CafeTypeMapEntity> existingCafeTypeMapEntity
                            = cafeTypeMapRepository.findByCafeTypeEntity(newCafeTypeEntity);

                    // 기존에 카페에 해당 타입이 이미 리뷰되어있는 상태면 typeReviewCount++
                    if (existingCafeTypeMapEntity.isPresent()) {
                        CafeTypeMapEntity cafeTypeMapEntity = existingCafeTypeMapEntity.get();
                        cafeTypeMapEntity.increaseTypeReviewCount();
                        return cafeTypeMapEntity;
                    }
                    // 카페에 해당 리뷰가 처음이면 새 객체 생성하여 저장
                    else {
                        return new CafeTypeMapEntity(cafeEntity, newCafeTypeEntity);
                    }
                })
                .forEach(updateCafeTypeMapEntities::add);

        cafeTypeMapRepository.saveAll(updateCafeTypeMapEntities);
    }

    /***
     * 카페 설명을 수정하는 메소드 (해당 카페를 등록한 사람만이 카페를 수정할 수 있습니다.)
     * @param memberId 카페 설명을 수정할려는 유저 ID
     * @param cafeId 카페 설명을 수정 당하는 카페 ID
     * @param cafeDescriptionUpdateReqDto 수정할 카페 설명
     */
    public void updateCafeDescription(Long memberId, String cafeId, CafeDescriptionUpdateReqDto cafeDescriptionUpdateReqDto) {

        CafeEntity cafeEntity = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CustomException(DOES_NOT_EXIST_CAFE));

        if(!Objects.equals(cafeEntity.getRegisterMember(), memberId)){
            throw new CustomException(NOT_AUTHORIZED_TO_EDIT_CAFE);
        }

        cafeEntity.updateCafeDescription(cafeDescriptionUpdateReqDto.cafeDescription());
        cafeRepository.save(cafeEntity);

    }
}

