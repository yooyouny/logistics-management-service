package com.sparta.order.presentation.response;

import com.sparta.order.domain.model.Order;
import com.sparta.order.domain.model.OrderState;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponse {
  private UUID orderId;
  private UUID supplierCompanyId;
  private UUID receiverCompanyId;
  private UUID managementHubId;
  private OrderState orderState;
  private LocalDateTime orderDate;
  private BigDecimal totalAmount;
  private int totalQuantity;
  private UUID deliveryId;
  private List<OrderDetailResponse> orderDetails;

  @Builder
  private OrderResponse(
      UUID orderId,
      UUID supplierCompanyId,
      UUID receiverCompanyId,
      UUID managementHubId,
      OrderState orderState,
      LocalDateTime orderDate,
      List<OrderDetailResponse> details,
      UUID deliveryId,
      BigDecimal totalAmount,
      int totalQuantity) {
    this.orderId = orderId;
    this.supplierCompanyId = supplierCompanyId;
    this.receiverCompanyId = receiverCompanyId;
    this.managementHubId = managementHubId;
    this.orderState = orderState;
    this.orderDate = orderDate;
    this.orderDetails = details;
    this.deliveryId = deliveryId;
    this.totalAmount = totalAmount;
    this.totalQuantity = totalQuantity;
  }

  public static OrderResponse fromEntity(Order order) {
    return OrderResponse.builder()
        .orderId(order.getOrderId())
        .supplierCompanyId(order.getSupplierCompanyId())
        .receiverCompanyId(order.getReceiverCompanyId())
        .managementHubId(order.getManagementHubId())
        .orderState(order.getOrderState())
        .orderDate(order.getOrderDate())
        .details(
            order.getOrderDetails().stream()
                .map(OrderDetailResponse::fromEntity)
                .collect(Collectors.toList()))
        .deliveryId(order.getDeliveryId())
        .totalAmount(order.getTotalAmount().amount())
        .totalQuantity(order.getTotalQuantity())
        .build();
  }
}
