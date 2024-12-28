package org.hidevelop.coffeecats.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "cafe")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CafeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cafeId;

    @Column(nullable = false)
    private String cafeName;

    private String cafeDescription;

    @Column(nullable = false, unique = true)
    private String loadAddress;

    @Comment("카페 위도")
    @Column(nullable = false)
    private double latitude;

    @Comment("카페 경도")
    @Column(nullable = false)
    private double longitude;

}
