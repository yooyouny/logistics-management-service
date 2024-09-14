package com.sparta.delivery.infrastructure.messaging;

import com.sparta.commons.domain.jpa.KafkaTopicConstant;
import com.sparta.delivery.application.DeliveryFacadeService;
import com.sparta.delivery.dto.DeliveryCreateDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "DeliveryCreateConsumer in DeliveryServer")
public class DeliveryCreateConsumer {
  private final DeliveryFacadeService deliveryFacadeService;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  @KafkaListener(topics = KafkaTopicConstant.CREATE_DELIVERY, groupId = "delivery")
  public void consume(
      @Header(name = "kafka_receivedMessageKey") String key, DeliveryCreateDto deliveryCreateDto) {
    UUID orderId = UUID.fromString(key);
    try {
      deliveryFacadeService.createDelivery(deliveryCreateDto, orderId);
    } catch (Exception e) {
      kafkaTemplate.send(
          KafkaTopicConstant.ERROR_IN_CREATE_DELIVERY, orderId.toString(), e.getMessage());
      log.error("Error occurred while create delivery : {}", e.getMessage());
    }
  }
}
