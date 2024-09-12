package com.sparta.order.presentation.response;

import com.sparta.order.domain.model.OrderDetail;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderDetailResponse {
  private UUID orderDetailId;
  private UUID productId;
  private String productName;
  private int quantity;
  private int unitPrice;

  @Builder
  private OrderDetailResponse(
      UUID orderDetailId, UUID productId, String productName, int quantity, int unitPrice) {
    this.orderDetailId = orderDetailId;
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public static OrderDetailResponse fromEntity(OrderDetail detail) {
    return OrderDetailResponse.builder()
        .orderDetailId(detail.getOrderDetailId())
        .productId(detail.getProductId())
        .productName(detail.getProductName())
        .quantity(detail.getQuantity())
        .unitPrice(detail.getUnitPrice())
        .build();
  }
}
