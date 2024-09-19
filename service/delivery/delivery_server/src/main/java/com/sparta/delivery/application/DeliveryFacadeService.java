package com.sparta.delivery.application;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.delivery.domain.model.Delivery;
import com.sparta.delivery.domain.model.State.DeliveryState;
import com.sparta.delivery.dto.DeliveryCreateDto;
import com.sparta.delivery.dto.DeliveryDto;
import com.sparta.delivery.infrastructure.client.CompanyClient;
import com.sparta.delivery.infrastructure.client.HubClient;
import com.sparta.delivery.presentation.dto.DeliveryResponse;
import com.sparta.delivery.presentation.exception.DeliveryErrorCode;
import com.sparta.hub.dto.InterHubResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "DeliveryFacadeService")
public class DeliveryFacadeService {
  private final DeliveryService deliveryService;
  private final DeliveryRouteService deliveryRouteService;
  private final HubClient hubClient;
  private final CompanyClient companyClient;

  @Transactional
  public DeliveryResponse createDelivery(DeliveryCreateDto request, UUID orderId) {
    UUID supplierHubId = getHubId(request.getSupplierCompanyId());
    UUID arrivalHubId = getHubId(request.getReceiverCompanyId());
    return DeliveryResponse.fromEntity(deliveryService.createDelivery(supplierHubId, arrivalHubId, request, orderId));
  }

  @Transactional
  public DeliveryResponse requestDeliveryAndCreateRoute(UUID deliveryId) {
    Delivery delivery = deliveryService.updateDeliveryState(deliveryId, DeliveryState.REQUESTED);
    InterHubResponse deliveryRoute =
        hubClient
            .getDeliveryRoutes(delivery.getDepartureHubId(), delivery.getArrivalHubId())
            .orElseThrow(() -> new BusinessException(DeliveryErrorCode.INTERNAL_SERVER_ERROR));
    delivery.setEstimatedDeliveryData(deliveryRoute.getElapsedTime(), deliveryRoute.getDistance());
    deliveryRouteService.createDeliveryRoute(delivery, deliveryRoute.getStops());
    return DeliveryResponse.fromEntity(delivery);
  }

  public List<DeliveryDto> findDeliveriesByShippingManagerId(
      UUID shippingManagerId, LocalDateTime requestedDateTime) {
    return deliveryService
        .getDeliveriesByShippingManager(shippingManagerId, requestedDateTime)
        .stream()
        .map(DeliveryMapper::toDeliveryDto)
        .collect(Collectors.toList());
  }

  private UUID getHubId(UUID companyId) {
    return companyClient
        .getHubIdByCompanyId(companyId)
        .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_HUB));
  }
}
