package com.sparta.order.presentation.controller;

import com.sparta.order.application.service.OrderService;
import com.sparta.order.dto.OrderDto;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/orders")
@Validated
public class OrderInternalController {
  private final OrderService orderService;

  @PreAuthorize("isAuthenticated() and (hasRole('ROLE_MASTER') or hasRole('ROLE_HUB_MANAGER'))")
  @GetMapping
  public Page<OrderDto> getOrderListByHubId(
      @NotNull @RequestParam("managementHubId") UUID hubId,
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "1000") int limit) {
    return orderService.getOrderListByHubId(hubId, offset, limit);
  }
}