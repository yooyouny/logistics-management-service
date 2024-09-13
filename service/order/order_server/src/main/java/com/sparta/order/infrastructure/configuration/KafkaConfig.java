package com.sparta.order.infrastructure.configuration;

import com.sparta.order.infrastructure.messaging.OrderProductDeductProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@ConditionalOnProperty(value = "kafka.enabled", matchIfMissing = true)
@RequiredArgsConstructor
@Configuration
public class KafkaConfig {
  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Bean
  public OrderProductDeductProducer orderProductDeductProducer() {
    return new OrderProductDeductProducer(kafkaTemplate);
  }

}
