package org.hidevelop.coffeecats.model.dto;

import jakarta.validation.constraints.NotBlank;

import org.hidevelop.coffeecats.model.entity.CafeEntity;
import org.hidevelop.coffeecats.model.type.CafeType;
import org.hidevelop.coffeecats.model.validator.constraint.ValidLatitude;
import org.hidevelop.coffeecats.model.validator.constraint.ValidLongitude;

import java.util.List;
import java.util.UUID;

public record RegisterCafeReqDto(
        @NotBlank(message = "카폐이름은 필수 값 입니다.")
        String cafeName,
        String cafeDescription,
        @NotBlank(message = "카폐주소는 필수 값 입니다.")
        String loadAddress,
        List<CafeType> cafeType,
        @ValidLatitude
        String latitude,
        @ValidLongitude
        String longitude
) {
    public CafeEntity toCafeEntity(String geoHash, double latitude, double longitude, Long registerMemberId) {
        return CafeEntity.builder()
                .cafeId(this.generateCafeUuid())
                .cafeName(this.cafeName)
                .cafeDescription(this.cafeDescription)
                .loadAddress(this.loadAddress)
                .latitude(latitude)
                .longitude(longitude)
                .geoHash(geoHash)
                .registerMember(registerMemberId)
                .build();

    }

    public UUID generateCafeUuid() {
        String combinedCafeString = String.format(
                "%s_%s_%s",
                this.cafeName,
                this.latitude,
                this.longitude
        );
        return UUID.nameUUIDFromBytes(combinedCafeString.getBytes());
    }


}
