package com.sparta.notification.infrastructure.feign.user;

import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserFeignClient {

  @GetMapping("/internal/shippingManagers/deliveryAgents")
  List<UUID> getDeliveryAgentIdList(@RequestParam String shippingManagerType);
}
