package com.sparta.user.infrastructure.repository.shipping_manager;

import com.sparta.user.domain.model.ShippingManager;
import com.sparta.user.domain.model.vo.ShippingManagerType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShippingManagerJpaRepository extends JpaRepository<ShippingManager, UUID> {

  boolean existsBySlackId(String slackId);

  @Query("SELECT sm.id FROM ShippingManager sm WHERE sm.type = :shippingManagerType")
  List<UUID> findIdsByType(ShippingManagerType shippingManagerType);
}
