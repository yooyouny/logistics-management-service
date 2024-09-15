package com.sparta.order.infrastructure.messaging;

import com.sparta.commons.domain.jpa.KafkaTopicConstant;
import com.sparta.order.application.service.OrderService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "ProductDeductErrorConsumer in OrderServer")
public class ProductDeductErrorConsumer {
  private final OrderService orderService;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  @KafkaListener(topics = KafkaTopicConstant.ERROR_IN_DEDUCT_PRODUCT_QUANTITY, groupId = "order")
  public void consume(
      @Payload String errorMessage, @Header(name = "kafka_receivedMessageKey") String orderId) {
    log.info("receive error message from Company Server : {}", errorMessage);
    orderService.cancelOrder(UUID.fromString(orderId));
    kafkaTemplate.send(KafkaTopicConstant.DELETE_DELIVERY, orderId);
    log.info("send DELETE_DELIVERY by OrderServer caused ERROR_IN_DEDUCT_PRODUCT_QUANTITY");
  }
}
