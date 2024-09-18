package com.sparta.delivery.infrastructure.repository;

import com.sparta.delivery.domain.model.Delivery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DeliveryRepositoryCustom {
  List<Delivery> findDeliveries(UUID shippingManagerId, LocalDateTime requestedDateTime);
}
