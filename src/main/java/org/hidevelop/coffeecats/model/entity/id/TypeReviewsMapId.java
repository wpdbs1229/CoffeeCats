package org.hidevelop.coffeecats.model.entity.id;


import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class TypeReviewsMapId implements Serializable {
    private Long cafeTypeEntityId;
    private Long memberCafeTypeReviewsEntityId;
}
