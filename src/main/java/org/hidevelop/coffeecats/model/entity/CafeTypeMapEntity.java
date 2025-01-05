package org.hidevelop.coffeecats.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cafe_type_map")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeTypeMapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cafe_id", nullable = false)
    private CafeEntity cafe;

    @ManyToOne
    @JoinColumn(name = "cafe_type_id", nullable = false)
    private CafeTypeEntity cafeType;


    public CafeTypeMapEntity(CafeEntity cafeEntity, CafeTypeEntity cafeTypeEntity) {
        this.cafe = cafeEntity;
        this.cafeType = cafeTypeEntity;
    }
}
