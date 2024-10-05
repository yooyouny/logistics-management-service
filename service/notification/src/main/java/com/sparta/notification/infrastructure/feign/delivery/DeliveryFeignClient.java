package com.sparta.notification.infrastructure.feign.delivery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "delivery")
public interface DeliveryFeignClient {

  // TODO. 반환 값 추후 수정
  @GetMapping("/internal/deliveries")
  List<String> getDeliveryListByShippingManagerId(
      @RequestParam UUID shippingManagerId, @RequestParam LocalDateTime shippingStartDate);
}
