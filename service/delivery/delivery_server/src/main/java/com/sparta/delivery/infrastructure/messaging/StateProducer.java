package com.sparta.delivery.infrastructure.messaging;

import com.sparta.delivery.dto.StateUpdateDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Slf4j(topic = "StateProducer from DeliveryServer")
public class StateProducer {
  private final KafkaTemplate<String, StateUpdateDto> kafkaTemplate;

  public void send(String topic, UUID orderId, StateUpdateDto stateDto){
    kafkaTemplate.send(topic, orderId.toString(), stateDto);
    log.info("send to kafka finished");
  }
}
