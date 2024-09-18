package com.sparta.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDto {
  private UUID orderId;
  private UUID supplierCompanyId;
  private UUID receiverCompanyId;
  private UUID managementHubId;
  private String orderState;
  private LocalDateTime orderDate;
  private BigDecimal totalAmount;
  private int totalQuantity;

  @Builder
  private OrderDto(
      UUID orderId,
      UUID supplierCompanyId,
      UUID receiverCompanyId,
      UUID managementHubId,
      String orderState,
      LocalDateTime orderDate,
      BigDecimal totalAmount,
      int totalQuantity) {
    this.orderId = orderId;
    this.supplierCompanyId = supplierCompanyId;
    this.receiverCompanyId = receiverCompanyId;
    this.managementHubId = managementHubId;
    this.orderState = orderState;
    this.orderDate = orderDate;
    this.totalAmount = totalAmount;
    this.totalQuantity = totalQuantity;
  }
}
