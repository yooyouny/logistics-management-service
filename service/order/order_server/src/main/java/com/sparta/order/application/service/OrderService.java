package com.sparta.order.application.service;

import com.sparta.order.domain.model.Order;
import com.sparta.order.domain.model.OrderDetail;
import com.sparta.order.infrastructure.repository.OrderDetailRepository;
import com.sparta.order.infrastructure.repository.OrderRepository;
import com.sparta.order.presentation.request.OrderDetailRequest;
import com.sparta.order.presentation.response.OrderResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;

  public OrderResponse create(
      UUID supplierCompanyId, UUID receiverCompanyId, List<OrderDetailRequest> requests) {
    Order order =
        Order.builder()
            .supplierCompanyId(supplierCompanyId)
            .receiverCompanyId(receiverCompanyId)
            .orderDate(LocalDateTime.now())
            .build();
    orderRepository.save(order);

    List<OrderDetail> orderDetails =
        requests.stream()
            .map(
                request ->
                    OrderDetail.builder()
                        .order(order)
                        .productId(request.productId())
                        .productName(request.productName())
                        .quantity(request.quantity())
                        .unitPrice(request.unitPrice())
                        .build())
            .collect(Collectors.toList());
    orderDetailRepository.saveAll(orderDetails);

    order.setOrderDetails(orderDetails);
    return OrderResponse.fromEntity(order);
  }
}
