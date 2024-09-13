package com.sparta.user.infrastructure.repository.shipping_manager;

import com.sparta.user.domain.model.ShippingManager;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingManagerJpaRepository extends JpaRepository<ShippingManager, UUID> {

  boolean existsBySlackId(String slackId);
}
