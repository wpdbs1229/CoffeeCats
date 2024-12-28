package org.hidevelop.coffeecats.model.dto;

import jakarta.validation.constraints.NotBlank;

import org.hidevelop.coffeecats.model.type.CafeType;
import org.hidevelop.coffeecats.model.validator.constraint.ValidLatitude;
import org.hidevelop.coffeecats.model.validator.constraint.ValidLongitude;

public record RegisterCafeReqDto(
        @NotBlank(message = "카폐이름은 필수 값 입니다.") String cafeName,
        String cafeDescription,
        @NotBlank(message = "카폐주소는 필수 값 입니다.") String loadAddress,
        CafeType cafeType,
        @ValidLatitude double latitude,
        @ValidLongitude double longitude
){

}
