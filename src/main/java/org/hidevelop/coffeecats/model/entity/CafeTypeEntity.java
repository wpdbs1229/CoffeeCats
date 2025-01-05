package org.hidevelop.coffeecats.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hidevelop.coffeecats.model.type.CafeType;

@Entity
@Table(name = "cafe_type")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cafeTypeId;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private CafeType cafeType;
}
