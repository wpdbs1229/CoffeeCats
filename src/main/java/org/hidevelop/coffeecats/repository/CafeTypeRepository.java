package org.hidevelop.coffeecats.repository;

import org.hidevelop.coffeecats.model.entity.CafeTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeTypeRepository extends JpaRepository<CafeTypeEntity, Long> {

}
