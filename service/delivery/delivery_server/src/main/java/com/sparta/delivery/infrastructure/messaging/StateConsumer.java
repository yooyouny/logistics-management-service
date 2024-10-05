package com.sparta.delivery.infrastructure.messaging;

import com.sparta.commons.domain.jpa.KafkaTopicConstant;
import com.sparta.commons.domain.jpa.UpdateStateRequest;
import com.sparta.delivery.application.DeliveryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "StateConsumer in DeliveryServer")
public class StateConsumer {
  private final DeliveryService deliveryService;

  @KafkaListener(topics = KafkaTopicConstant.UPDATE_ORDER_STATE, groupId = "update-deliveryState")
  public void consume(
      @Header(name = "kafka_receivedMessageKey") String key, UpdateStateRequest request) {
    UUID deliveryId = UUID.fromString(request.getTargetId());
    switch (request.getStateName()) {
      case "CANCELLED" -> deliveryService.deleteDelivery(deliveryId);
      case "CONFIRMED" -> deliveryService.confirm(deliveryId);
    }
    log.info("update Delivery state : {}", deliveryId);
  }
}
