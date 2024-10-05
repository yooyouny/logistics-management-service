package com.sparta.delivery.infrastructure.messaging;

import com.sparta.commons.domain.jpa.UpdateStateRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Slf4j(topic = "StateProducer from DeliveryServer")
public class StateProducer {
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void send(String topic, UUID deliveryId, UpdateStateRequest request) {
    kafkaTemplate.send(topic, deliveryId.toString(), request);
    log.info("send to kafka finished");
  }
}
