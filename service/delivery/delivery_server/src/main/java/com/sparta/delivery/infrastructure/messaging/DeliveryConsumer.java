package com.sparta.delivery.infrastructure.messaging;

import com.sparta.commons.domain.jpa.KafkaTopicConstant;
import com.sparta.delivery.application.DeliveryFacadeService;
import com.sparta.delivery.application.DeliveryService;
import com.sparta.delivery.dto.DeliveryCreateDto;
import com.sparta.delivery.presentation.dto.DeliveryResponse;
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
@Slf4j(topic = " in DeliveryServer")
public class DeliveryConsumer {
  private final DeliveryFacadeService deliveryFacadeService;
  private final DeliveryService deliveryService;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  @KafkaListener(topics = KafkaTopicConstant.CREATE_DELIVERY, groupId = "create-delivery")
  public void consume(
      @Header(name = "kafka_receivedMessageKey") String key, DeliveryCreateDto deliveryCreateDto) {
    UUID orderId = UUID.fromString(key);
    try {
      DeliveryResponse delivery = deliveryFacadeService.createDelivery(deliveryCreateDto, orderId);
      kafkaTemplate.send(
          KafkaTopicConstant.SET_DELIVERY_IN_ORDER, orderId.toString(), delivery.getDeliveryId());
    } catch (Exception e) {
      kafkaTemplate.send(
          KafkaTopicConstant.ERROR_IN_CREATE_DELIVERY, orderId.toString(), deliveryCreateDto);
      log.error("Error occurred while create delivery : {}", e.getMessage());
    }
  }

  @KafkaListener(topics = KafkaTopicConstant.DELETE_DELIVERY, groupId = "delete-delivery")
  public void consume(@Header(name = "kafka_receivedMessageKey") String key) {
    UUID orderId = UUID.fromString(key);
    deliveryService.deleteDelivery(orderId);
    log.info("Delete Delivery from OrderServer caused by ERROR_IN_DEDUCT_PRODUCT_QUANTITY");
  }
}
