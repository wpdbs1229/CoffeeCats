package org.hidevelop.coffeecats.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hidevelop.coffeecats.model.entity.id.CafeTypeMapId;

@IdClass(CafeTypeMapId.class)
@Entity
@Table(name = "cafe_type_map")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeTypeMapEntity extends BaseEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "cafe_id", nullable = false)
    private CafeEntity cafeEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "cafe_type_id", nullable = false)
    private CafeTypeEntity cafeTypeEntity;

    @Comment("해당 태그가 리뷰된 수")
    private int typeReviewCount;

    public CafeTypeMapEntity(CafeEntity cafeEntity, CafeTypeEntity cafeTypeEntity) {
        this.cafeEntity = cafeEntity;
        this.cafeTypeEntity = cafeTypeEntity;
        this.typeReviewCount = 1;
    }

}
