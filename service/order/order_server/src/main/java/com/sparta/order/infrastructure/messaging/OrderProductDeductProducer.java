package com.sparta.order.infrastructure.messaging;

import com.sparta.order.dto.ProductDeductDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
@RequiredArgsConstructor
@Slf4j(topic = "OrderProductDeductProducer in OrderServer")
public class OrderProductDeductProducer {
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void send(String topic, UUID productId, List<ProductDeductDto> dto) {// TODO :: company_dto로 옮기기
    kafkaTemplate.send(topic, productId.toString(), dto);
    log.info("send to kafka finished");
  }
}