package com.sparta.order.infrastructure.repository;

import com.sparta.order.domain.model.OrderDetail;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {}
