package org.hidevelop.coffeecats.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String cafeTypeName;
}
