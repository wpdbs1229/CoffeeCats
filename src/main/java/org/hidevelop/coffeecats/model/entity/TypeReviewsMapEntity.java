package org.hidevelop.coffeecats.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hidevelop.coffeecats.model.entity.id.TypeReviewsMapId;

@Getter
@Entity
@IdClass(TypeReviewsMapId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TypeReviewsMapEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "cafe_type_id", nullable = false)
    private CafeTypeEntity cafeTypeEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "member_cafe_type_reviews_id", nullable = false)
    private MemberCafeTypeReviewsEntity memberCafeTypeReviewsEntity;

    public TypeReviewsMapEntity(CafeTypeEntity cafeTypeEntity, MemberCafeTypeReviewsEntity reviewsEntity){
        this.cafeTypeEntity = cafeTypeEntity;
        this.memberCafeTypeReviewsEntity = reviewsEntity;
    }
}