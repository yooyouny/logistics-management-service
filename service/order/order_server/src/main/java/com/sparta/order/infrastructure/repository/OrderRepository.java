package com.sparta.order.infrastructure.repository;

import com.sparta.order.domain.model.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {
  Optional<Order> findByOrderId(UUID orderID);
}
