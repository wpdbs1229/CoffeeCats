package org.hidevelop.coffeecats.repository;

import org.hidevelop.coffeecats.model.entity.CafeTypeEntity;
import org.hidevelop.coffeecats.model.type.CafeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CafeTypeRepository extends JpaRepository<CafeTypeEntity, Long> {

    Optional<CafeTypeEntity> findByCafeType(CafeType cafeType);
}
