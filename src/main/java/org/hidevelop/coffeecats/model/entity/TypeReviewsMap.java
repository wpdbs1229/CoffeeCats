package org.hidevelop.coffeecats.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hidevelop.coffeecats.model.entity.id.TypeReviewsMapId;

@Entity
@IdClass(TypeReviewsMapId.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TypeReviewsMap {

    @Id
    @ManyToOne
    @JoinColumn(name = "cafe_type_id", nullable = false)
    private CafeTypeEntity cafeTypeEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "member_cafe_type_reviews_id", nullable = false)
    private MemberCafeTypeReviews memberCafeTypeReviews;
}