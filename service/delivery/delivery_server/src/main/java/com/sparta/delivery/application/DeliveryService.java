package com.sparta.delivery.application;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.delivery.domain.model.Delivery;
import com.sparta.delivery.domain.model.State.DeliveryState;
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

  public Delivery updateDeliveryState(UUID deliveryId) {
    Delivery delivery =
        deliveryRepository
            .findByDeliveryId(deliveryId)
            .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
    delivery.updateDeliveryState(DeliveryState.REQUESTED);
    return delivery;
  }
}
