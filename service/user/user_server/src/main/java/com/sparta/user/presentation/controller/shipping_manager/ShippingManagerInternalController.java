package com.sparta.user.presentation.controller.shipping_manager;

import com.sparta.user.application.service.shipping_manager.ShippingManagerInternalService;
import com.sparta.user.domain.model.vo.ShippingManagerType;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/shipping-managers")
public class ShippingManagerInternalController {

  private final ShippingManagerInternalService shippingManagerInternalService;

  @GetMapping("/deliveryAgents")
  List<UUID> getDeliveryAgentIdList(@RequestParam ShippingManagerType shippingManagerType) {
    return shippingManagerInternalService.getDeliveryAgentIdList(shippingManagerType);
  }
}
