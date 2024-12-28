package org.hidevelop.coffeecats.repository;

import org.hidevelop.coffeecats.model.entity.CafeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeRepository extends JpaRepository<CafeEntity, Long> {

}
