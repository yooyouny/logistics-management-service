package com.sparta.order.infrastructure.messaging;

import com.sparta.commons.domain.jpa.KafkaTopicConstant;
import com.sparta.company_dto.ProductDeductDto;
import com.sparta.order.application.service.OrderService;
import java.util.List;
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
@Slf4j(topic = "DeliveryCreateErrorConsumer in OrderServer")
public class DeliveryCreateErrorConsumer {
  private final OrderService orderService;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  @KafkaListener(topics = KafkaTopicConstant.ERROR_IN_CREATE_DELIVERY, groupId = "order")
  public void consume(
      @Payload List<ProductDeductDto> dto, @Header(name = "kafka_receivedMessageKey") String orderId) {
    orderService.cancelOrder(UUID.fromString(orderId));
    kafkaTemplate.send(KafkaTopicConstant.REVERT_PRODUCT_QUANTITY, orderId, dto);
    log.info("send REVERT_PRODUCT_QUANTITY to CompanyServer caused ERROR_IN_CREATE_DELIVERY");
  }
}
