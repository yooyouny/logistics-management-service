package com.sparta.delivery.application;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.delivery.domain.model.Delivery;
import com.sparta.delivery.infrastructure.client.HubClient;
import com.sparta.delivery.presentation.dto.DeliveryResponse;
import com.sparta.delivery.presentation.exception.DeliveryErrorCode;
import com.sparta.hub.dto.InterHubResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "DeliveryFacadeService")
public class DeliveryFacadeService {
  private DeliveryService deliveryService;
  private DeliveryRouteService deliveryRouteService;
  private HubClient hubClient;

  @Transactional
  public DeliveryResponse requestDeliveryAndCreateRoute(UUID deliveryId){
    Delivery delivery = deliveryService.updateDeliveryState(deliveryId);
    InterHubResponse deliveryRoute =
        hubClient
            .getDeliveryRoutes(delivery.getDepartureHubId(), delivery.getArrivalHubId())
            .orElseThrow(() -> new BusinessException(DeliveryErrorCode.INTERNAL_SERVER_ERROR));
    delivery.setEstimatedDeliveryData(deliveryRoute.getElapsedTime(), deliveryRoute.getDistance());
    deliveryRouteService.createDeliveryRoute(delivery, deliveryRoute.getStops());
    return DeliveryResponse.fromEntity(delivery);
  }
}
