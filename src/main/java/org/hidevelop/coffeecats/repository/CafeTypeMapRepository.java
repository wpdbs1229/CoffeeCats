package org.hidevelop.coffeecats.repository;

import org.hidevelop.coffeecats.model.entity.CafeTypeMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeTypeMapRepository extends JpaRepository<CafeTypeMapEntity, Long> {

}
