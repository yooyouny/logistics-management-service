package com.sparta.delivery.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryCreateDto {
  private UUID orderId;
  private UUID supplierCompanyId;
  private UUID receiverCompanyId;
  private String shippingAddress;
  private UUID shippingManagerId;
  private String shippingManagerSlackId;

  @Builder
  public DeliveryCreateDto(
      UUID orderId,
      UUID supplierCompanyId,
      UUID receiverCompanyId,
      String shippingAddress,
      UUID shippingManagerId,
      String shippingManagerSlackId) {
    this.orderId = orderId;
    this.supplierCompanyId = supplierCompanyId;
    this.receiverCompanyId = receiverCompanyId;
    this.shippingAddress = shippingAddress;
    this.shippingManagerId = shippingManagerId;
    this.shippingManagerSlackId = shippingManagerSlackId;
  }
}
