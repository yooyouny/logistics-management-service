package com.sparta.order.application.service;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.order.domain.model.Order;
import com.sparta.order.domain.model.OrderDetail;
import com.sparta.order.domain.model.OrderState;
import com.sparta.order.dto.OrderDto;
import com.sparta.order.infrastructure.repository.OrderDetailRepository;
import com.sparta.order.infrastructure.repository.OrderRepository;
import com.sparta.order.presentation.exception.OrderErrorCode;
import com.sparta.order.presentation.request.OrderDetailRequest;
import com.sparta.order.presentation.response.OrderResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;

  public OrderResponse create(
      UUID supplierCompanyId, UUID receiverCompanyId, UUID managementHubId, List<OrderDetailRequest> requests) {
    Order order =
        Order.builder()
            .supplierCompanyId(supplierCompanyId)
            .receiverCompanyId(receiverCompanyId)
            .orderDate(LocalDateTime.now())
            .managementHubId(managementHubId)
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
  @Transactional(readOnly = true)
  public OrderResponse get(UUID orderId){
    return orderRepository.findByOrderId(orderId)
        .map(OrderResponse::fromEntity)
        .orElseThrow(() -> new BusinessException(OrderErrorCode.NOT_FOUND_ORDER));
  }

  @Transactional(readOnly = true)
  public Page<OrderDto> getOrderListByHubId(UUID managementHubId, int offset, int limit){
    Pageable pageable = PageRequest.of(offset, limit, Sort.by("orderDate").descending());
    return orderRepository.findAllByManagementHubId(managementHubId, pageable)
        .map(OrderMapper::toOrderDto);
  }

  public void cancelOrder(UUID orderId) {
    Order order =
        orderRepository
            .findByOrderId(orderId)
            .orElseThrow(() -> new BusinessException(OrderErrorCode.NOT_FOUND_ORDER));

    order.updateOrderState(OrderState.CANCELLED);
  }
}
