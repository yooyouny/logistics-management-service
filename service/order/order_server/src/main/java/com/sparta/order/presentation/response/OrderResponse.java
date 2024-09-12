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
  private OrderState orderState;
  private LocalDateTime orderDate;
  private BigDecimal totalAmount;
  private int totalQuantity;
  private List<OrderDetailResponse> orderDetails;

  @Builder
  private OrderResponse(
      UUID orderId,
      UUID supplierCompanyId,
      UUID receiverCompanyId,
      OrderState orderState,
      LocalDateTime orderDate,
      List<OrderDetailResponse> details,
      BigDecimal totalAmount,
      int totalQuantity) {
    this.orderId = orderId;
    this.supplierCompanyId = supplierCompanyId;
    this.receiverCompanyId = receiverCompanyId;
    this.orderState = orderState;
    this.orderDate = orderDate;
    this.orderDetails = details;
    this.totalAmount = totalAmount;
    this.totalQuantity = totalQuantity;
  }

  public static OrderResponse fromEntity(Order order) {
    return OrderResponse.builder()
        .orderId(order.getOrderId())
        .supplierCompanyId(order.getSupplierCompanyId())
        .receiverCompanyId(order.getReceiverCompanyId())
        .orderState(order.getOrderState())
        .orderDate(order.getOrderDate())
        .details(
            order.getOrderDetails().stream()
                .map(OrderDetailResponse::fromEntity)
                .collect(Collectors.toList()))
        .totalAmount(order.getTotalAmount().amount())
        .totalQuantity(order.getTotalQuantity())
        .build();
  }
}
