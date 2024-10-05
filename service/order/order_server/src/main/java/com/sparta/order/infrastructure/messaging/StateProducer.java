package com.sparta.order.infrastructure.messaging;

import com.sparta.commons.domain.jpa.UpdateStateRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Slf4j(topic = "StateProducer in OrderServer")
public class StateProducer {
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void send(String topic, UUID orderId, UpdateStateRequest request) {
    log.info("update Order State with {} : {} ", request.getStateName(), orderId);
    kafkaTemplate.send(topic, orderId.toString(), request);
    log.info("send to deliveryServer");
  }
}
