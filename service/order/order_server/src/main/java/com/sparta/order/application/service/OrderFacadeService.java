package com.sparta.order.application.service;

import com.sparta.commons.domain.jpa.KafkaTopicConstant;
import com.sparta.order.dto.ProductDeductDto;
import com.sparta.order.infrastructure.messaging.OrderProductDeductProducer;
import com.sparta.order.presentation.request.OrderCreateRequest;
import com.sparta.order.presentation.response.OrderDetailResponse;
import com.sparta.order.presentation.response.OrderResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderFacadeService {
  private final OrderService orderService;
  private final OrderProductDeductProducer productProducer;

  public OrderResponse create(OrderCreateRequest request) {
    OrderResponse order =
        orderService.create(
            request.supplierCompanyId(), request.receiverCompanyId(), request.orderDetails());

    List<ProductDeductDto> productDto = getProductDto(order.getOrderDetails());

    productProducer.send(
        KafkaTopicConstant.deduct_product_quantity.name(), order.getOrderId(), productDto);

    //TODO :: 배송생성
    return order;
  }

  private List<ProductDeductDto> getProductDto(List<OrderDetailResponse> details) {
    return details.stream()
        .map(detail -> new ProductDeductDto(detail.getProductId(), detail.getQuantity()))
        .collect(Collectors.toList());
  }
}
