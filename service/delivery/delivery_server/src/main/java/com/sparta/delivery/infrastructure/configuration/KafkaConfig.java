package com.sparta.delivery.infrastructure.configuration;

import com.sparta.delivery.application.DeliveryService;
import com.sparta.delivery.dto.StateUpdateDto;
import com.sparta.delivery.infrastructure.messaging.StateProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@ConditionalOnProperty(value = "kafka.enabled", matchIfMissing = true)
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
  private final KafkaTemplate<String, StateUpdateDto> kafkaTemplate;

  @Bean
  public StateProducer stateProducer(){
    return new StateProducer(kafkaTemplate);
  }
}
