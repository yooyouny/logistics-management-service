package com.sparta.order.application.service;

import com.sparta.order.presentation.request.OrderCreateRequest;
import com.sparta.order.presentation.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderFacadeService {
  private final OrderService orderService;
  private final DeliveryService deliveryService;

  public OrderResponse create(OrderCreateRequest request) {
    OrderResponse newOrder =
        orderService.create(
            request.supplierCompanyId(), request.receiverCompanyId(), request.orderDetails());
    // TODO :: 배송, 배송경로 생성 <- 비동기로 생성하게끔 던지기 재고차감, 트랜잭션 예외처리
    return newOrder;
  }
}
