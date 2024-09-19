package com.sparta.order.application.service;

import com.sparta.order.domain.model.Order;
import com.sparta.order.dto.OrderDto;

public class OrderMapper {
  public static OrderDto toOrderDto(Order order) {
    return OrderDto.builder()
        .orderId(order.getOrderId())
        .supplierCompanyId(order.getSupplierCompanyId())
        .receiverCompanyId(order.getReceiverCompanyId())
        .managementHubId(order.getManagementHubId())
        .totalQuantity(order.getTotalQuantity())
        .totalAmount(order.getTotalAmount().amount())
        .orderState(order.getOrderState().name())
        .orderDate(order.getOrderDate())
        .build();
  }
}
