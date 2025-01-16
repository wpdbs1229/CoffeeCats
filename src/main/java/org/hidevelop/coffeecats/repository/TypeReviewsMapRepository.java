package org.hidevelop.coffeecats.repository;

import org.hidevelop.coffeecats.model.entity.TypeReviewsMapEntity;
import org.hidevelop.coffeecats.model.entity.id.TypeReviewsMapId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface TypeReviewsMapRepository extends JpaRepository<TypeReviewsMapEntity, TypeReviewsMapId> {

    @Query("select trme from TypeReviewsMapEntity as trme where trme.memberCafeTypeReviewsEntity.memberCafeTypeReviewId = :memberCafeTypeReviewId")
    List<TypeReviewsMapEntity> findByMemberCafeTypeReviewId(@Param("memberCafeTypeReviewId") Long memberCafeTypeReviewId);
}
