package com.sparta.order.application.service;

import com.sparta.commons.domain.jpa.KafkaTopicConstant;
import com.sparta.company_dto.ProductDeductDto;
import com.sparta.delivery.dto.DeliveryCreateDto;
import com.sparta.order.infrastructure.messaging.DeliveryCreateProducer;
import com.sparta.order.infrastructure.messaging.ProductDeductProducer;
import com.sparta.order.presentation.request.OrderCreateRequest;
import com.sparta.order.presentation.response.OrderDetailResponse;
import com.sparta.order.presentation.response.OrderResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j(topic = "OrderFacadeService")
public class OrderFacadeService {
  private final OrderService orderService;
  private final ProductDeductProducer productProducer;
  private final DeliveryCreateProducer deliveryProducer;

  public OrderResponse create(OrderCreateRequest request) {
    OrderResponse order =
        orderService.create(
            request.supplierCompanyId(),
            request.receiverCompanyId(),
            request.managementHubId(),
            request.orderDetails());

    log.info("orderId: {}", order.getOrderId());
    List<ProductDeductDto> productDto = getProductDto(order.getOrderDetails());
    DeliveryCreateDto deliveryDto = createDeliveryDto(request);

    productProducer.send(
        KafkaTopicConstant.DEDUCT_PRODUCT_QUANTITY, order.getOrderId(), productDto);
    deliveryProducer.send(KafkaTopicConstant.CREATE_DELIVERY, order.getOrderId(), deliveryDto);
    return order;
  }

  private DeliveryCreateDto createDeliveryDto(OrderCreateRequest request) {
    return DeliveryCreateDto.builder()
        .supplierCompanyId(request.supplierCompanyId())
        .receiverCompanyId(request.receiverCompanyId())
        .shippingManagerId(request.shippingManagerId())
        .shippingManagerSlackId(request.shippingManagerSlackId())
        .shippingAddress(request.shippingAddress())
        .build();
  }

  private List<ProductDeductDto> getProductDto(List<OrderDetailResponse> details) {
    return details.stream()
        .map(detail -> new ProductDeductDto(detail.getProductId(), detail.getQuantity()))
        .collect(Collectors.toList());
  }
}
