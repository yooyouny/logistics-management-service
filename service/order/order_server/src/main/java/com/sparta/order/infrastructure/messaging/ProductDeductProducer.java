package com.sparta.order.infrastructure.messaging;

import com.sparta.company_dto.ProductDeductDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
@RequiredArgsConstructor
@Slf4j(topic = "ProductDeductProducer in OrderServer")
public class ProductDeductProducer {
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void send(String topic, UUID orderId, List<ProductDeductDto> dto) {
    kafkaTemplate.send(topic, orderId.toString(), dto);
    log.info("send ProductDeductDto of {} to CompanyServer", orderId);
  }
}