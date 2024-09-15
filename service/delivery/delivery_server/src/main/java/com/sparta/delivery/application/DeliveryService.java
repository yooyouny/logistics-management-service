package com.sparta.delivery.application;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.delivery.domain.model.Delivery;
import com.sparta.delivery.domain.model.State.DeliveryState;
import com.sparta.delivery.dto.DeliveryCreateDto;
import com.sparta.delivery.infrastructure.repository.DeliveryRepository;
import com.sparta.delivery.presentation.exception.DeliveryErrorCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {
  private final DeliveryRepository deliveryRepository;

  public void createDelivery(
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
    deliveryRepository.save(delivery);
  }

  public Delivery updateDeliveryState(UUID deliveryId) {
    Delivery delivery =
        deliveryRepository
            .findByDeliveryId(deliveryId)
            .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
    delivery.updateDeliveryState(DeliveryState.REQUESTED);
    return delivery;
  }

  public void deleteDelivery(UUID orderId) {
    Delivery delivery =
        deliveryRepository
            .findByOrderId(orderId)
            .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
    deliveryRepository.delete(delivery);
  }
}
