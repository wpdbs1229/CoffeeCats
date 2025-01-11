package org.hidevelop.coffeecats.model.entity.id;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class CafeTypeMapId implements Serializable {
    private Long cafe;
    private Long cafeType;
}
