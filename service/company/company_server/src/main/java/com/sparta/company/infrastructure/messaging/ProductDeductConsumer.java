package com.sparta.company.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.commons.domain.jpa.KafkaTopicConstant;
import com.sparta.company.application.service.ProductService;
import com.sparta.company_dto.ProductDeductDto;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
@Slf4j(topic = "ProductDeductConsumer in CompanyServer")
public class ProductDeductConsumer {
  private final ProductService productService;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  @KafkaListener(topics = KafkaTopicConstant.DEDUCT_PRODUCT_QUANTITY, groupId = "company")
  public void consume(
      @Header(name = "kafka_receivedMessageKey") String key,
      @Payload List<LinkedHashMap<String, Object>> payload) {
    UUID orderId = UUID.fromString(key);
    List<ProductDeductDto> deductDto = payload.stream()
        .map(map -> {
          ObjectMapper mapper = new ObjectMapper();
          return mapper.convertValue(map, ProductDeductDto.class);
        })
        .collect(Collectors.toList());

    try {
      productService.deductProductQuantity(deductDto);
    } catch (Exception e) {
      kafkaTemplate.send(
          KafkaTopicConstant.ERROR_IN_DEDUCT_PRODUCT_QUANTITY, orderId.toString(), e.getMessage());
      log.error("Error occurred while deduct product : {}", e.getMessage());
    }
  }
}
