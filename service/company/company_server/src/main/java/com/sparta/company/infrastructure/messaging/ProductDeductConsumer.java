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

  @KafkaListener(topics = KafkaTopicConstant.DEDUCT_PRODUCT_QUANTITY, groupId = "deduct-product")
  public void handleProductQuantityDeduction(
      @Header(name = "kafka_receivedMessageKey") String key,
      @Payload List<LinkedHashMap<String, Object>> payload) {
    UUID orderId = UUID.fromString(key);
    List<ProductDeductDto> deductDto = getDeserializedProductDto(payload);
    try {
      productService.deductProductQuantity(deductDto);
    } catch (Exception e) {
      kafkaTemplate.send(
          KafkaTopicConstant.ERROR_IN_DEDUCT_PRODUCT_QUANTITY, orderId.toString(), e.getMessage());
      log.error("Error occurred while deduct product : {}", e.getMessage());
    }
  }

  @KafkaListener(topics = KafkaTopicConstant.REVERT_PRODUCT_QUANTITY, groupId = "revert-product")
  public void onProductQuantityError(
      @Header(name = "kafka_receivedMessageKey") String orderId,
      @Payload List<LinkedHashMap<String, Object>> payload) {
    List<ProductDeductDto> deductDto = getDeserializedProductDto(payload);
    productService.revertProductQuantity(deductDto);
  }

  private List<ProductDeductDto> getDeserializedProductDto(
      List<LinkedHashMap<String, Object>> payload) {
    return payload.stream()
        .map(
            map -> {
              ObjectMapper mapper = new ObjectMapper();
              return mapper.convertValue(map, ProductDeductDto.class);
            })
        .collect(Collectors.toList());
  }
}
