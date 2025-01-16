package org.hidevelop.coffeecats.repository;

import org.hidevelop.coffeecats.model.entity.MemberCafeTypeReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface MemberCafeTypeReviewsRepository extends JpaRepository<MemberCafeTypeReviewsEntity, Long> {

    @Query("select mctr from MemberCafeTypeReviewsEntity as mctr "
            + "join fetch mctr.memberEntity m "
            + "join fetch mctr.cafeEntity c "
            + "where m.memberId = :memberId and c.cafeId = :cafeId")
    Optional<MemberCafeTypeReviewsEntity> findIdByMemberIdAndCafeId(
            @Param("memberId") Long memberId,
            @Param("cafeId") String cafeId
    );

}

