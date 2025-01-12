package org.hidevelop.coffeecats.repository;

import org.hidevelop.coffeecats.model.entity.CafeTypeMapEntity;
import org.hidevelop.coffeecats.model.entity.id.CafeTypeMapId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeTypeMapRepository extends JpaRepository<CafeTypeMapEntity, CafeTypeMapId> {
}
