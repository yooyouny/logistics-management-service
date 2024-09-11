package com.sparta.order.presentation.controller;

import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.order.application.service.OrderFacadeService;
import com.sparta.order.presentation.request.OrderCreateRequest;
import com.sparta.order.presentation.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderFacadeService orderFacadeService;

  @PostMapping
  public ResponseBody<OrderResponse> create(@RequestBody @Valid OrderCreateRequest request) {
    return new SuccessResponseBody<>(orderFacadeService.create(request));
  }
}
