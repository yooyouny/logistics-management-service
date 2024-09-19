package com.sparta.delivery.application;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.delivery.domain.model.Delivery;
import com.sparta.delivery.domain.model.State.DeliveryState;
import com.sparta.delivery.dto.DeliveryCreateDto;
import com.sparta.delivery.infrastructure.repository.DeliveryRepository;
import com.sparta.delivery.infrastructure.repository.DeliveryRepositoryImpl;
import com.sparta.delivery.presentation.dto.DeliveryResponse;
import com.sparta.delivery.presentation.exception.DeliveryErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {
  private final DeliveryRepository deliveryRepository;
  private final DeliveryRepositoryImpl deliveryQueryRepository;

  public Delivery createDelivery(
      UUID departureHubId, UUID arrivalHubId, DeliveryCreateDto request, UUID orderId) {
    Delivery delivery =
        Delivery.builder()
            .departureHubId(departureHubId)
            .arrivalHubId(arrivalHubId)
            .orderId(orderId)
            .shippingAddress(request.getShippingAddress())
            .shippingManagerId(request.getShippingManagerId())
            .shippingManagerSlackId(request.getShippingManagerSlackId())
            .build();
    return deliveryRepository.save(delivery);
  }

  public Delivery updateDeliveryState(UUID deliveryId, DeliveryState state) {
    Delivery delivery = getDelivery(deliveryId);
    delivery.updateDeliveryState(state);
    return delivery;
  }

  @Transactional(readOnly = true)
  public DeliveryResponse get(UUID deliveryId) {
    return deliveryRepository
        .findByDeliveryId(deliveryId)
        .map(DeliveryResponse::fromEntity)
        .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
  }

  public void deleteDelivery(UUID deliveryId) {
    Delivery delivery = getDelivery(deliveryId);
    deliveryRepository.delete(delivery);
  }

  public void confirm(UUID deliveryId){
    Delivery delivery = getDelivery(deliveryId);
    delivery.updateDeliveryState(DeliveryState.CONFIRMED);
  }

  public List<Delivery> getDeliveriesByShippingManager(
      UUID shippingManagerId, LocalDateTime shippingStartDate) {
    return deliveryQueryRepository.findDeliveries(shippingManagerId, shippingStartDate);
  }

  private Delivery getDelivery(UUID deliveryId){
    return deliveryRepository
            .findByDeliveryId(deliveryId)
            .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
  }
}
