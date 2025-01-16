package org.hidevelop.coffeecats.repository;

import org.hidevelop.coffeecats.model.entity.CafeTypeEntity;
import org.hidevelop.coffeecats.model.entity.CafeTypeMapEntity;
import org.hidevelop.coffeecats.model.entity.id.CafeTypeMapId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CafeTypeMapRepository extends JpaRepository<CafeTypeMapEntity, CafeTypeMapId> {

    Optional<CafeTypeMapEntity> findByCafeTypeEntity(CafeTypeEntity cafeTypeEntity);
}
