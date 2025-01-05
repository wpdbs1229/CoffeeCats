package org.hidevelop.coffeecats.service;

import com.github.davidmoten.geo.GeoHash;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.exception.CustomException;
import org.hidevelop.coffeecats.model.dto.RegisterCafeReqDto;
import org.hidevelop.coffeecats.model.entity.CafeEntity;
import org.hidevelop.coffeecats.model.entity.CafeTypeEntity;
import org.hidevelop.coffeecats.model.entity.CafeTypeMapEntity;
import org.hidevelop.coffeecats.model.type.CafeType;
import org.hidevelop.coffeecats.repository.CafeRepository;
import org.hidevelop.coffeecats.repository.CafeTypeMapRepository;
import org.hidevelop.coffeecats.repository.CafeTypeRepository;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

import static org.hidevelop.coffeecats.exception.error.impl.CafeError.*;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    private final CafeTypeRepository cafeTypeRepository;
    private final CafeTypeMapRepository cafeTypeMapRepository;

    @Transactional(rollbackOn = CustomException.class)
    public void registerCafe(RegisterCafeReqDto registerCafeReqDto, Long registerMemberId) {

        validateCafeRegistration(registerCafeReqDto);

        double latitude = valueToMapPoint(registerCafeReqDto.latitude());
        double longitude = valueToMapPoint(registerCafeReqDto.longitude());

        String geoHash = generateGeoHash(latitude, longitude);


        Set<CafeTypeEntity> cafeTypeEntitySet = new HashSet<>();

        for(CafeType cafeType : registerCafeReqDto.cafeType()){
            cafeTypeEntitySet.add(cafeTypeRepository.findByCafeType(cafeType)
                    .orElseThrow(() -> new CustomException(DOES_NOT_EXIST_CAFE_TYPE)));
        }


        CafeEntity cafeEntity = cafeRepository.save(
                registerCafeReqDto.toCafeEntity(
                        geoHash,
                        latitude,
                        longitude,
                        registerMemberId));

        for (CafeTypeEntity cafeTypeEntity :cafeTypeEntitySet){
            cafeTypeMapRepository.save(new CafeTypeMapEntity(cafeEntity, cafeTypeEntity));
        }

    }

    private void validateCafeRegistration(RegisterCafeReqDto registerCafeReqDto) {

        boolean existsById = cafeRepository.existsById(registerCafeReqDto.generateCafeUuid());
        if (existsById) {
            throw new CustomException(ALREADY_REGISTERED);
        }

        boolean existsByLoadAddress = cafeRepository.existsByLoadAddress(registerCafeReqDto.loadAddress());
        if(existsByLoadAddress) {
            throw new CustomException(ALREADY_REGISTERED);
        }

    }

    private double valueToMapPoint(String value) {
        return switch (value.charAt(0)) {
            case '1', '3' -> Double.parseDouble(value) / 10000000.0;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    private String generateGeoHash(double latitude, double longitude) {
        return GeoHash.encodeHash(latitude, longitude, 7);
    }
}
