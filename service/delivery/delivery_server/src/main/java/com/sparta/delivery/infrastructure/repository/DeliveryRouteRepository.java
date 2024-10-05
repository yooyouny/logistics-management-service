package com.sparta.delivery.infrastructure.repository;

import com.sparta.delivery.domain.model.DeliveryRoute;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRouteRepository extends JpaRepository<DeliveryRoute, UUID> {}
