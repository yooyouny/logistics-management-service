package com.sparta.order.infrastructure.messaging;

import com.sparta.commons.domain.jpa.KafkaTopicConstant;
import com.sparta.order.application.service.OrderService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "DeliveryCreateErrorConsumer in OrderServer")
public class DeliveryCreateErrorConsumer {
  private final OrderService orderService;

  @KafkaListener(topics = KafkaTopicConstant.ERROR_IN_CREATE_DELIVERY, groupId = "order")
  public void consume(
      @Payload String errorMessage, @Header(name = "kafka_receivedMessageKey") String orderId) {
    orderService.cancelOrder(UUID.fromString(orderId));
    log.info("receive error message from Delivery Server : {}", errorMessage);
  }
}
