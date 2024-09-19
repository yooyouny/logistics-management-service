package com.sparta.order.presentation.controller;

import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.order.application.service.OrderFacadeService;
import com.sparta.order.application.service.OrderService;
import com.sparta.order.presentation.request.OrderCreateRequest;
import com.sparta.order.presentation.response.CompanyOrderResponse;
import com.sparta.order.presentation.response.OrderResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
  private final OrderFacadeService orderFacadeService;
  private final OrderService orderService;

  @PostMapping
  public ResponseBody<OrderResponse> create(@RequestBody @Valid OrderCreateRequest request) {
    return new SuccessResponseBody<>(orderFacadeService.create(request));
  }

  @GetMapping("/{orderId}")
  public ResponseBody<OrderResponse> get(@NotNull @PathVariable("orderId") UUID orderId) {
    return new SuccessResponseBody<>(orderService.get(orderId));
  }

  @GetMapping
  public ResponseBody<CompanyOrderResponse> getCompanyOrderList(
      @NotNull @RequestParam UUID companyId,
      @RequestParam LocalDateTime startDate,
      @RequestParam LocalDateTime endDate) {
    return new SuccessResponseBody<>(
        orderService.getCompanyOrderList(companyId, startDate, endDate));
  }

  @PutMapping("/{orderId}/cancel")
  public SuccessResponseBody<OrderResponse> cancel(@NotNull @PathVariable("orderId") UUID orderId) {
    return new SuccessResponseBody<>(orderFacadeService.cancel(orderId));
  }

  @PutMapping("/{orderId}/confirm")
  public SuccessResponseBody<OrderResponse> confirm(
      @NotNull @PathVariable("orderId") UUID orderId) {
    return new SuccessResponseBody<>(orderFacadeService.confirm(orderId));
  }
}
