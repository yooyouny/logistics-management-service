package com.sparta.delivery.infrastructure.repository;

import com.sparta.delivery.domain.model.Delivery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {}
