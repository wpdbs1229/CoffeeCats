package org.hidevelop.coffeecats.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberCafeTypeReviewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCafeTypeReviewId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "cafe_id", nullable = false)
    private CafeEntity cafeEntity;

    public MemberCafeTypeReviewsEntity(MemberEntity memberEntity, CafeEntity cafeEntity) {
        this.memberEntity = memberEntity;
        this.cafeEntity = cafeEntity;
    }
}
