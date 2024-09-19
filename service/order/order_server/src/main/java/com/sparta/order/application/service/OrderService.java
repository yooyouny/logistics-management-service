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
import com.sparta.order.presentation.response.CompanyOrderResponse;
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
      UUID supplierCompanyId,
      UUID receiverCompanyId,
      UUID managementHubId,
      List<OrderDetailRequest> requests) {
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
  public OrderResponse get(UUID orderId) {
    return orderRepository
        .findByOrderId(orderId)
        .map(OrderResponse::fromEntity)
        .orElseThrow(() -> new BusinessException(OrderErrorCode.NOT_FOUND_ORDER));
  }

  @Transactional(readOnly = true)
  public CompanyOrderResponse getCompanyOrderList(
      UUID companyId, LocalDateTime startDate, LocalDateTime endDate) {
    if (endDate == null) {
      endDate = LocalDateTime.now();
      if (startDate == null) {
        startDate = endDate.minusWeeks(1); // 일주일 동안의 기간으로 디폴트값 지정
      }
    }

    List<OrderResponse> suppliedOrders =
        orderRepository
            .findAllBySupplierCompanyIdAndOrderDateBetween(companyId, startDate, endDate)
            .stream()
            .map(OrderResponse::fromEntity)
            .collect(Collectors.toList());
    List<OrderResponse> receivedOrders =
        orderRepository
            .findAllByReceiverCompanyIdAndOrderDateBetween(companyId, startDate, endDate)
            .stream()
            .map(OrderResponse::fromEntity)
            .collect(Collectors.toList());

    return CompanyOrderResponse.toResponse(
        companyId, suppliedOrders, receivedOrders, startDate, endDate);
  }

  @Transactional(readOnly = true)
  public Page<OrderDto> getOrderListByHubId(UUID managementHubId, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset, limit, Sort.by("orderDate").descending());
    return orderRepository
        .findAllByManagementHubId(managementHubId, pageable)
        .map(OrderMapper::toOrderDto);
  }

  public OrderResponse cancelOrder(UUID orderId) {
    Order order = getOrder(orderId);
    if (order.getOrderState() == OrderState.SHIPPED) {
      throw new BusinessException(OrderErrorCode.CONFLICT_CANCEL_ORDER);
    }
    order.updateOrderState(OrderState.CANCELLED);
    return OrderResponse.fromEntity(order);
  }

  public OrderResponse confirmOrder(UUID orderId) {
    Order order = getOrder(orderId);
    order.updateOrderState(OrderState.CONFIRMED);
    return OrderResponse.fromEntity(order);
  }

  public void setDelivery(UUID orderId, UUID deliveryId) {
    Order order = getOrder(orderId);
    order.setDeliveryId(deliveryId);
  }

  private Order getOrder(UUID orderId) {
    return orderRepository
        .findByOrderId(orderId)
        .orElseThrow(() -> new BusinessException(OrderErrorCode.NOT_FOUND_ORDER));
  }
}
