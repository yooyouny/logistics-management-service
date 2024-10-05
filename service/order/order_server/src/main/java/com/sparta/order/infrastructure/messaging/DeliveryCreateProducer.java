package com.sparta.order.infrastructure.messaging;

import com.sparta.delivery.dto.DeliveryCreateDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Slf4j(topic = "DeliveryCreateProducer in OrderServer")
public class DeliveryCreateProducer {
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void send(String topic, UUID orderId, DeliveryCreateDto dto) {
    kafkaTemplate.send(topic, orderId.toString(), dto);
    log.info("send DeliveryDto of {} to DeliveryServer", orderId);
  }
}
