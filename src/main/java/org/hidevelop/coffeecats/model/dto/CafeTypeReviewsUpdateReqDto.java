package org.hidevelop.coffeecats.model.dto;

import jakarta.validation.constraints.NotEmpty;
import org.hidevelop.coffeecats.model.type.CafeType;
import java.util.List;

public record CafeTypeReviewsUpdateReqDto (
        @NotEmpty
        List<CafeType> cafeType
){
}
