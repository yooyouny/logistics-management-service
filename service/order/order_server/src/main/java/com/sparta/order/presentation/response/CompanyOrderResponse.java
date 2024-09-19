package com.sparta.order.presentation.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CompanyOrderResponse {
  private UUID companyId;
  private List<OrderResponse> suppliedOrders;  // 우리 회사가 공급한 주문
  private List<OrderResponse> receivedOrders;  // 우리 회사가 받은 주문
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  @Builder
  private CompanyOrderResponse(UUID companyId, List<OrderResponse> suppliedOrders,
      List<OrderResponse> receivedOrders, LocalDateTime startDate, LocalDateTime endDate) {
    this.companyId = companyId;
    this.suppliedOrders = suppliedOrders;
    this.receivedOrders = receivedOrders;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public static CompanyOrderResponse toResponse(
      UUID companyId,
      List<OrderResponse> suppliedOrders,
      List<OrderResponse> receivedOrders,
      LocalDateTime startDate,
      LocalDateTime endDate) {
    return CompanyOrderResponse.builder()
        .companyId(companyId)
        .suppliedOrders(suppliedOrders)
        .receivedOrders(receivedOrders)
        .startDate(startDate)
        .endDate(endDate)
        .build();
    }
}