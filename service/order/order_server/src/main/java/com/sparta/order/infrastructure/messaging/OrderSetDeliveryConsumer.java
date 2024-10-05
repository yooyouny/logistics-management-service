package com.sparta.order.infrastructure.messaging;

import com.sparta.commons.domain.jpa.KafkaTopicConstant;
import com.sparta.order.application.service.OrderService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@RequiredArgsConstructor
@Slf4j(topic = "OrderSetDeliveryConsumer in OrderServer")
public class OrderSetDeliveryConsumer {
  private final OrderService orderService;

  @KafkaListener(topics = KafkaTopicConstant.SET_DELIVERY_IN_ORDER, groupId = "order")
  public void consume(
      @Header(name = "kafka_receivedMessageKey") String orderId, @Payload String deliveryId) {
    orderService.setDelivery(UUID.fromString(orderId), UUID.fromString(deliveryId));
    log.info("set Delivery in Order : {}", orderId);
  }
}
