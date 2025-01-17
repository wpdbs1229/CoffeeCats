package org.hidevelop.coffeecats.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CafeDescriptionUpdateReqDto(
        @NotEmpty
        @Size(min = 10, max = 500, message = "최소 10글자, 최대 500글자까지 적을 수 있습니다.")
        String cafeDescription
) {
}
